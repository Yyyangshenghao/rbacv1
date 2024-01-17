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
  data() {
    return {
      attendanceId: ''
    };
  },
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
        const response = await this.$http.post('/face/recognize', {
          image: base64Image,
          employeeId: window.sessionStorage.getItem("userId")
        });
        if (response.data.status === "success") {
          alert("签到成功！");
          await this.updateAttendanceRecord();
        }
      } catch (error) {
        console.error('Error during face recognition:', error);
      }
    },
    // 更新出勤记录的方法
    async updateAttendanceRecord() {
      try {
        let attendanceId = this.attendanceId;
        // 使用反引号 ` 来确保模板字符串正确工作
        const { data: res } = await this.$http.post(`/attendance/update/${attendanceId}`);
        // 检查响应中的状态
        if (res.code === 200) {
          alert('签到记录更新成功');
          // 在这里添加其他成功后的逻辑
        } else {
          // 处理更新失败的情况
          alert('签到记录更新失败: ' + updateResponse.data.msg);
        }
      } catch (error) {
        console.error('Error during attendance record update:', error);
      }
    }

  },
  created() {
    this.attendanceId = this.$route.query.attendanceId;
    // 在这里你可以根据 attendanceId 做进一步的操作
  }
};
</script>
