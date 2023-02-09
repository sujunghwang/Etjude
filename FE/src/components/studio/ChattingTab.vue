<template>
  <div>
    <div>{{ stompClient }}</div>
    <div>스튜디오 아이디 : {{ studioId }}</div>
    <br />
    씬 넘버:
    <label for="씬넘버"><input v-model="sceneNumber" type="text" /></label>
    <br />
    유저 아이디:
    <label for="유저아이디"><input v-model="userId" type="text" /></label>
    <br />
    <button @click="sendRecordingStatus(`REC`)">녹화 시작</button>
    <button @click="sendRecordingStatus(`DONE`)">녹화 끝</button>
    <br />
    <hr />
    유저닉네임:
    <input v-model="nickname" type="text" />
    내용: <input v-model="message" type="text" @keyup.enter="sendMessage" />
    <hr />
    <div class="container">
      <div>
        채팅 내역
        <div v-for="(item, idx) in recvList" :key="idx">
          <div v-if="item.studioId === studioId">
            <h3>현재스튜디오: {{ item.studioId }}</h3>
            <h5>{{ item.chatTime }} : 닉네임: {{ item.nickname }} | 내용: {{ item.content }}</h5>
          </div>
        </div>
      </div>
      <div>
        녹화 현황
        <div v-for="(recording, idx) in recordingList" :key="idx">
          <h3>씬넘버: {{ recording.sceneNumber }}</h3>
          <h3>유저이름: {{ recording.user.userId }}</h3>
          <h3>녹화상황: {{ recording.status }}</h3>
          <h3>===================</h3>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { sendMessage, sendRecordingStatus, send, connect } from "@/api/chat";

export default {
  name: "ChattingTab",
  data() {
    return {
      studioId: "1",
      sceneNumber: "",
      userId: "",
      nickname: "",
      message: "",
      recvList: [],
      recordingList: [],
      serverURL: "",
    };
  },
  async created() {
    this.connect();
  },
  methods: {
    sendMessage,
    sendRecordingStatus,
    send,
    connect,
  },
};
</script>
<style lang=""></style>
