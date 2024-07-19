let stompClient = null;
let userNickname = null;

// 페이지 로드 시 실행
window.onload = function() {
    setNickname();
    connect();
};

function setNickname() {
    userNickname = prompt('Enter your nickname:');
    while (!userNickname || userNickname.trim() === '') {
        userNickname = prompt('Nickname cannot be empty. Please enter your nickname:');
    }
    userNickname = userNickname.trim();
    showMessage({from: 'System', content: userNickname + ' (으)로 접속되었습니다.'});
}



function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket); //simple Text Oriented Messaging Protocol
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
        // 연결 성공 시 시스템 메시지 표시
        showMessage({from: 'System', content: '서버에 정상적으로 접속 되었습니다.'});
    });
}

function sendMessage() {
    const messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            from: userNickname,
            content: messageContent
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function showMessage(message) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('message');

    const senderElement = document.createElement('span');
    senderElement.classList.add('sender');

    const contentElement = document.createElement('span');
    contentElement.classList.add('content');


    if (message.from === 'System') {
        messageElement.classList.add('system');
        senderElement.textContent = 'System: ';
        contentElement.textContent = message.content;
    } else {
        senderElement.textContent = message.from + ': ';
        contentElement.textContent = message.content;
        if (message.from === userNickname) {
            messageElement.classList.add('sent');
        } else {
            messageElement.classList.add('received');
        }
    }

    messageElement.appendChild(senderElement);
    messageElement.appendChild(contentElement);
    messageElement.appendChild(timeElement);
    document.getElementById('messages').appendChild(messageElement);

    const messagesContainer = document.getElementById('messages');
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}


document.getElementById('message').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});