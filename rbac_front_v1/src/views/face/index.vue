<template>
  <div class="camera">
    <video ref="video" width="640" height="480" autoplay></video>
    <button @click="captureAndProcessImage">拍照并处理</button>
    <canvas ref="canvas" width="640" height="480" style="display: none;"></canvas>
  </div>
</template>

<script>
export default {
  name: "CameraComponent",
  data(){
    return {
      employeeId: window.sessionStorage.getItem("userId")
    }
  },
  methods: {
    async setupCamera() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        this.$refs.video.srcObject = stream;
      } catch (error) {
        console.error('Error accessing the camera:', error);
      }
    },
    captureAndProcessImage() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      const video = this.$refs.video;
      context.drawImage(video, 0, 0, canvas.width, canvas.height);

      // 将图像转换为Base64编码的字符串
      let base64Image = canvas.toDataURL('image/png');
      // 去除Base64字符串中的前缀
      base64Image = base64Image.replace(/^data:image\/png;base64,/, '');
      //console.log(base64Image); // 打印到控制台或发送到后端

      this.sendImageToBackend(base64Image);

    },
    async sendImageToBackend(base64Image) {
      try {
        const payload = {
          base64Image: base64Image,
          employeeId: this.employeeId,
          // 可以添加其他需要的数据
        };
        const response = await this.$http.post('/image/upload-image', payload);
        if(response.data.status === 'fail'){
          if(response.data.msg === 'No face detected in the image.'){
            alert("未检测到人脸，请重新拍照！");
          }
        }
        else {alert("上传成功！")}
      } catch (error) {
        console.error('Error sending the image to backend:', error);
      }
    }
  },
  mounted() {
    this.setupCamera();
  }
};
</script>

<style scoped>
.camera {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
