<template>
  <div>
    <el-card class="box-card">
      <div class="camera">
        <video ref="video" width="640" height="480" autoplay></video>
        <canvas ref="canvas" width="640" height="480" style="display: none;"></canvas>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "FaceRecognitionComponent",

  mounted() {
    this.setupCamera();
    this.startFaceDetection();
  },

  beforeDestroy() {
    clearInterval(this.intervalId);
  },

  methods: {
    // 设置摄像头
    async setupCamera() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        this.$refs.video.srcObject = stream;
      } catch (error) {
        console.error('Error accessing the camera:', error);
      }
    },

    startFaceDetection() {
      this.intervalId = setInterval(this.captureAndRecognize, 1000); // 每秒检测一次
    },

    // 捕获图像并进行人脸识别
    async captureAndRecognize() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      const video = this.$refs.video;
      context.drawImage(video, 0, 0, canvas.width, canvas.height);

      let base64Image = canvas.toDataURL('image/png');
      base64Image = base64Image.replace(/^data:image\/png;base64,/, '');

      try {
        // 发送图像到后端进行识别
        const response = await this.$http.post('/face/recognize', { image: base64Image });
        if (response.data.status === 'success') {
          alert('识别成功: ' + response.data.user);
        } else {
          alert('识别失败');
        }
      } catch (error) {
        console.error('Error during face recognition:', error);
      }
    }
  }
};
</script>
