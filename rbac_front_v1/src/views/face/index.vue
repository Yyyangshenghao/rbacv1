<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>人脸识别</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <div class="camera">
        <video ref="video" width="640" height="480" autoplay></video>
        <el-button class="button-spacing" type="primary" @click="captureAndProcessImage">拍照并处理</el-button>
        <el-button class="button-spacing" type="success" @click="trainFaceData">开始训练人脸数据</el-button>
        <canvas ref="canvas" width="640" height="480" style="display: none;"></canvas>
      </div>
    </el-card>
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
    },
    async trainFaceData() {
      try {
        const response = await this.$http.post('/face/train', {
          employeeId: this.employeeId// 传递必要的参数，比如员工ID
        });

        if (response.data.status === 'success') {
          alert('人脸数据训练开始');
        } else {
          alert('人脸数据训练请求失败');
        }
      } catch (error) {
        console.error('Error sending training request:', error);
      }
    },

  },
  created: function() {
    this.setupCamera();
    this.sendImageToBackend();
  }
};
</script>

<style scoped>
.box-card {
  max-width: 700px;
  margin: 0 auto;
}

.camera {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.button-spacing {
  margin: 10px; /* 上下左右都添加10px的间距 */
}
</style>