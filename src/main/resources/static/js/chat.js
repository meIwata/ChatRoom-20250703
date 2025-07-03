let ws;
let myNick = "";
const chat = document.getElementById("chat");
const nicknameInput = document.getElementById("nickname");
const joinBtn = document.getElementById("joinBtn");
const msgInput = document.getElementById("msg");
const sendBtn = document.getElementById("sendBtn");

function connect() {
    myNick = nicknameInput.value.trim();
    if (!myNick) {
        alert("請輸入暱稱！");
        return;
    }
    // 自動偵測協議與主機，支援 ngrok 及本機
    const wsProtocol = location.protocol === "https:" ? "wss:" : "ws:";
    const wsHost = location.host;
    ws = new WebSocket(`${wsProtocol}//${wsHost}/chat`);
    ws.onopen = () => {
        ws.send(myNick);
        nicknameInput.disabled = true;
        joinBtn.disabled = true;
        sendBtn.disabled = false;
        msgInput.focus();
    };
    ws.onmessage = (e) => {
        let msg = e.data;
        let className = "chat-msg";
        if (msg.startsWith("[" + myNick + "]")) className += " me";
        if (msg.includes("進入聊天室") || msg.includes("離開聊天室") || msg.includes("請輸入暱稱")) className += " system";
        chat.innerHTML += `<div class="${className}">${escapeHtml(msg)}</div>`;
        chat.scrollTop = chat.scrollHeight;
    };
    ws.onclose = () => {
        chat.innerHTML += `<div class="chat-msg system">您已離線或聊天室已關閉</div>`;
        nicknameInput.disabled = false;
        joinBtn.disabled = false;
        sendBtn.disabled = true;
    };
    ws.onerror = () => {
        chat.innerHTML += `<div class="chat-msg system">連線錯誤，請重新整理</div>`;
    };
}
function sendMsg() {
    let msg = msgInput.value.trim();
    if (!ws || ws.readyState !== WebSocket.OPEN) {
        alert("請先加入聊天室！");
        return;
    }
    if (!msg) return;
    ws.send(msg);
    msgInput.value = "";
    msgInput.focus();
}
// 防止 XSS
function escapeHtml(text){
    return text.replace(/[&<>"']/g,function(c){
        return {"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#039;"}[c];
    });
}