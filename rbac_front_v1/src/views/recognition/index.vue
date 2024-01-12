<template>
  <div>
    <video ref="video" width="640" height="480" autoplay></video>
    <button @click="captureImage">拍照并上传</button>
    <input type="file" @change="onFileSelected">
    <button @click="uploadFile">上传图片</button>
    <canvas ref="canvas" width="640" height="480" style="display: none;"></canvas>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      employeeId: window.sessionStorage.getItem('userId'),
      base64Image: "",
      selectedFile: null,
    }
  },
  mounted() {
    this.setupCamera();
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
    captureImage() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      context.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height);
      let base64 = canvas.toDataURL('image/png');
      this.uploadImage(base64);
    },
    async uploadImage(base64) {
      try {
        const base64Data = base64.replace(/^data:image\/[a-z]+;base64,/, '');
        let payload = {
          base64Image: base64Data,
          employeeId: this.employeeId
        };
        await this.$http.post('/face/upload', payload);
        console.log('Image uploaded successfully');
        console.log(payload);
      } catch (error) {
        console.error('Error uploading the image:', error);
      }
    },
    onFileSelected(event) {
      this.selectedFile = event.target.files[0];
    },
    uploadFile() {
      const formData = new FormData();
      formData.append('file', this.selectedFile);

      axios.post('/face/uploadFile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            console.log('File uploaded successfully');
          })
          .catch(error => {
            console.error('Error uploading the file:', error);
          });
    }
  }
};
</script>
