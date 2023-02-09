import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export function sendMessage() {
  if (this.nickname !== "" && this.message !== "") {
    this.send();
    this.message = "";
  }
}
export function sendRecordingStatus(e) {
  if (this.sceneNumber !== "" && this.userId !== "") {
    if (this.stompClient && this.stompClient.connected && e === `REC`) {
      this.stompClient.send(
        `/pub/api/v1/studio/chat/status/${this.studioId}/${this.sceneNumber}/${this.userId}/${e}`,
        {}
      );
    } else if (this.stompClient && this.stompClient.connected && e === `DONE`) {
      this.stompClient.send(
        `/pub/api/v1/studio/chat/status/${this.studioId}/${this.sceneNumber}/${this.userId}/${e}`,
        {}
      );
    }
  }
}
export function send() {
  // console.log(`Send message: ${this.message}`);
  if (this.stompClient && this.stompClient.connected) {
    this.stompClient.send(
      `/pub/api/v1/studio/chat/${this.studioId}/${this.nickname}`,
      JSON.stringify(this.message),
      {}
    );
  }
}
export function connect() {
  this.serverURL = `http://localhost:8080/api/v1/studio/chat`;
  const socket = new SockJS(this.serverURL);
  this.stompClient = Stomp.over(socket);
  // console.log(`소켓 연결을 시도합니다. 서버 주소:${this.serverURL}`);
  this.stompClient.connect({}, () => {
    // 소켓 연결 성공
    this.connected = true;
    // console.log(`소켓 연결 성공 ${frame}`);
    // 서버의 메시지 전송 endpoint를 구독합니다.
    // console.log("========================");
    this.stompClient.subscribe(`/sub/api/v1/studio/chat/${this.studioId}`, (res) => {
      // console.log("구독으로 받은 메시지 입니다.", res.body);
      // console.log(typeof res.body);
      // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
      this.recvList.push(JSON.parse(res.body));
    });
    this.stompClient.subscribe(`/status/api/v1/studio/chat/${this.studioId}`, (res) => {
      // console.log("구독으로 받은 녹화 상태 입니다.", res.body);
      const parsedData = JSON.parse(res.body);
      if (parsedData.status === "REC") {
        for (let i = 0; i < this.recordingList.length; i += 1) {
          if (this.recordingList[i].sceneNumber === parsedData.sceneNumber) {
            this.recordingList.splice(i, 1);
          }
        }
        this.recordingList.push(parsedData);
      } else if (parsedData.status === "DONE") {
        for (let i = 0; i < this.recordingList.length; i += 1) {
          if (this.recordingList[i].sceneNumber === parsedData.sceneNumber) {
            this.recordingList.splice(i, 1);
          }
        }
      }

      // 녹화 현황 리스트 확인용 console
      // for (let i = 0; i < this.recordingList.length; i += 1) {
      // console.log(
      // `씬넘버 = ${this.recordingList[i].sceneNumber}, 유저 아이디 = ${this.recordingList[i].user.userId}`
      // );
      // }
    });
  });
}
