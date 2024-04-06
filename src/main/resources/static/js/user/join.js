// 전화번호 입력 필드에 숫자만 입력되도록 처리하는 함수
function PhoneNumber(input) {
    // 입력된 값에서 숫자만 추출
    var phoneNumber = input.value.replace(/[^0-9]/g, "");
    // 숫자만 입력된 값으로 입력 필드의 값을 설정
    input.value = phoneNumber;
}

// 전화번호 입력 필드의 입력 이벤트에 함수 연결
document.getElementById("user_phone").addEventListener("input", function () {
    PhoneNumber(this);
});

function phone(e) {
    var number = e.value.replace(/[^0-9]/g, "");
    var phone = "";
    if (number.length < 4) {
        return number;
    } else {
        phone += number.substr(0, 3);
        phone += "-";
        phone += number.substr(3, 4);
        phone += "-";
        phone += number.substr(7, 4);
    }
    e.value = phone;
}

function checkPasswordMatch() {
    var pw = document.getElementById("pw").value;
    var repw = document.getElementById("repw").value;
    var pwCheckMsg = document.getElementById("pw_check");

    if (pw !== repw) {
        pwCheckMsg.innerText = "비밀번호가 일치하지 않습니다.";
        pwCheckMsg.style.color = "red";
        pwCheckMsg.style.fontSize = "13px"

    } else {
        pwCheckMsg.innerText = ""; // 일치할 경우 메시지를 지움
    }
}

// repw 입력 필드의 입력 이벤트에 함수 연결
document.getElementById("repw").addEventListener("input", function() {
    checkPasswordMatch();
});


function pwCheck() {
    var pw = document.getElementById("pw").value;
    var repw = document.getElementById("repw").value;

    if (pw !== repw) {
        alert("비밀번호를 확인해주세요.");
        return false; // 폼 제출을 막음
    }
    return true; // 폼 제출을 허용
}


function checkJoinButton() {
    if (emailAuthFinish) {
        // 인증이 완료되었을 때
        document.getElementById("joinButton").disabled = false; // 회원가입 버튼 활성화
        document.getElementById("joinButton").classList.remove("btn-outline-secondary"); // btn-outline-secondary 클래스 제거
        document.getElementById("joinButton").classList.add("btn-outline-primary"); // btn-outline-primary 클래스 추가
    } else {
        // 인증이 완료되지 않았을 때
        document.getElementById("joinButton").disabled = true; // 회원가입 버튼 비활성화
        document.getElementById("joinButton").classList.remove("btn-outline-primary"); // btn-outline-primary 클래스 제거
        document.getElementById("joinButton").classList.add("btn-outline-secondary"); // btn-outline-secondary 클래스 추가
    }
}

function confirmed() {
    const userResponse = window.confirm("회원가입을 정말 취소하시겠습니까?");
    if (userResponse) {
        window.location.href = "/user/login";
    } else {
    }
}

function setJoinDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
    var day = ('0' + currentDate.getDate()).slice(-2);
    var formattedDate = year + '-' + month + '-' + day;
    // user_joindate 필드의 값을 설정
        document.getElementById('user_joindate').value = formattedDate;
}

let isPasswordVisible = false;

function togglePassword() {
    const passwordInput = document.getElementById("pw");
    const toggleButton = document.getElementById("pwToggle");

    if (isPasswordVisible) {
        passwordInput.type = "password";
        toggleButton.innerHTML = '<i class="bi bi-eye"></i>';
    } else {
        passwordInput.type = "text";
        toggleButton.innerHTML = '<i class="bi bi-eye-slash"></i>';
    }
    isPasswordVisible = !isPasswordVisible;
}

function togglerePassword() {
    const passwordInput = document.getElementById("repw");
    const toggleButton = document.getElementById("repwToggle");

    if (isPasswordVisible) {
        passwordInput.type = "password";
        toggleButton.innerHTML = '<i class="bi bi-eye"></i>';
    } else {
        passwordInput.type = "text";
        toggleButton.innerHTML = '<i class="bi bi-eye-slash"></i>';
    }
    isPasswordVisible = !isPasswordVisible;
}

const idCheck = () => {
    const user_id = document.getElementById("user_id").value;
    const id_info = document.getElementById("id_info");
    if (user_id.trim() === "") {
        alert("아이디를 입력하세요.")
        return;
    }
    console.log("입력값: ", user_id);
    $.ajax({
        type: "post",
        url: "/user/id-check",
        data: {
            "user_id": user_id
        },
        success: function (res) {
            console.log("요청성공", res);
            if (res == "ok") {
                console.log("사용 가능한 아이디");
                id_info.innerHTML = "사용 가능한 아이디입니다.";
                id_info.style.color = "blue"
                id_info.style.fontSize = "13px"

            } else {
                console.log("이미 사용중인 아이디입니다");
                id_info.innerHTML = "이미 사용중인 아이디입니다.";
                id_info.style.color = "red"
                id_info.style.fontSize = "13px"
            }
        },
        error: function (err) {
            console.log("에러발생", err);
        }
    });
}
const nickCheck = () => {
    const user_nick = document.getElementById("user_nick").value;
    const nick_info = document.getElementById("nick_info");
    if (user_nick.trim() === "") {
        alert("닉네임을 입력하세요.")
        return;
    }
    console.log("입력값: ", user_nick);
    $.ajax({
        type: "post",
        url: "/user/nick-check",
        data: {
            "user_nick": user_nick
        },
        success: function (res) {
            console.log("요청성공", res);
            if (res == "ok") {
                console.log("사용 가능한 닉네임");
                nick_info.innerHTML = "사용 가능한 닉네임입니다.";
                nick_info.style.color = "blue"
                nick_info.style.fontSize = "13px"
            } else {
                console.log("이미 사용중인 닉네임입니다");
                nick_info.innerHTML = "이미 사용중인 닉네임입니다.";
                nick_info.style.color = "red"
                nick_info.style.fontSize = "13px"
            }
        },
        error: function (err) {
            console.log("에러발생", err);
        }
    });
}

let timer1;
let emailTimer = false; //타이머 작동여부

function mailCheck() {
    // 이메일 입력 필드에서 이메일 주소 가져오기
    let user_email = document.getElementById("user_email").value;

    // 이메일 주소가 비어 있는지 확인
    if (user_email.trim() === "") {
        alert("이메일 주소를 입력해주세요.");
        return;
    }

    console.log(user_email);
    // 여기에 이메일 주소의 유효성을 검사하는 코드를 추가할 수 있습니다.
    // 예를 들어 정규 표현식을 사용하여 이메일 형식을 확인할 수 있습니다.

    // 유효한 이메일 주소인 경우, 서버로 이메일 전송 요청을 보냅니다.
    $.ajax({
        type: "POST",
        url: "/send-email",
        data: {
            user_email: user_email
        },
        success: function (response) {
            if (response === "success") {
                // 이메일 전송 성공 시
                alert("이메일이 성공적으로 전송되었습니다. 인증번호를 확인해주세요.");
                if (emailTimer === false) {
                    document.getElementById("emailConfirmNum").style.display = "block"; // 인증번호 입력 영역 표시
                    document.getElementById("emailFinish").disabled = false; // 인증 확인 버튼 활성화
                    let time = 180;
                    timer1 = setInterval(function () {
                        if (time >= 0) {
                            const min = Math.floor(time / 60);
                            const sec = String(time % 60).padStart(2, "0");
                            document.getElementById("timer1").innerText = min + ":" + sec;
                            time = time - 1;
                        } else {
                            time += 1;
                            const min = Math.floor(time / 60);
                            const sec = String(time % 60).padStart(2, "0");
                            document.getElementById("emailFinish").disabled = true; // 인증 확인 버튼 비활성화
                            emailTimer = false;
                            clearInterval(timer1); // 타이머 중지
                            document.getElementById("emailAuthKey").value = ""; // 인증번호 입력 필드 초기화
                        }
                    }, 1000);
                }
            } else if (response === "duplicate") {
                // 중복된 이메일인 경우
                alert("이미 사용중인 이메일입니다.");
            }
        },
        error: function (err) {
            alert("이메일 전송에 실패했습니다. 다시 시도해주세요.");
            console.error("이메일 전송 오류:", err);
        }
    });
}


// "이메일 인증 확인" 버튼 클릭 시 실행되는 함수
function emailAuthFinish() {
    // 사용자가 입력한 값
    let emailAuthKey = document.getElementById("emailAuthKey").value;
    console.log(emailAuthKey);

    $.ajax({
        type: "post",
        url: "/authKey-check",
        data: {
            "emailAuthKey" : emailAuthKey
        },
        success: function (res){
            console.log("요청성공", res);
            console.log("입력한 인증번호"+emailAuthKey);
            if(res == "ok"){
                console.log("인증 성공");
                alert("인증이 완료되었습니다.");
                clearInterval(timer1); // 타이머 중지
                document.getElementById("emailToken").disabled = true;
                document.getElementById("emailFinish").textContent = "인증 완료"; //인증 확인 버튼을 인증 완료 버튼으로 변경
                document.getElementById("emailFinish").disabled = true;
                document.getElementById("timer1").innerText = ""; // 인증번호 입력 필드 초기화

            }else {
                console.log("인증 실패");
                alert("인증번호가 맞지 않습니다.");

            }
        },
        error: function (err){
            console.log("에러발생", err);
        }

    });
    checkJoinButton(); // 회원가입 버튼 상태를 확인하여 변경

}


//지도

var map;
var marker;

//주소검색 검색 api
function openZipSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr;
            if (data.jibunAddress) {
                addr = data.jibunAddress;
            } else if (data.autoJibunAddress) {
                addr = data.autoJibunAddress;
            } else {
                // 주소 정보가 없는 경우에 대한 처리
                addr = "주소 정보를 찾을 수 없습니다.";
            }
            var addr2 = data.sigungu + " " + data.bname;
            console.log("검색된 지번주소:" +addr);
            console.log("검색된 구+동 주소:" +addr2);

            $("#user_location").val(addr);

            //아래에 지정한 함수 바로 불러오기
            searchAddress();
        }
    }).open();
}

//주소를 좌표로 변환 후 지도 표시하기
function searchAddress() {
    //user_location에 입력된 값 가져오기
    var addr = document.getElementById('user_location').value;
    // 장소 검색 객체를 생성합니다.
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다.
    geocoder.addressSearch(addr, function(result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도 중심을 검색된 좌표로 이동시킵니다.
            map.setCenter(coords);

            // 마커를 생성하고 지도 위에 표시합니다.
            marker.setPosition(coords);
        } else {
            alert('검색에 실패하였습니다.');
        }
    });
}


navigator.geolocation.getCurrentPosition(function(position){
    // 현재 위치의 위도와 경도를 가져옴
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;
    console.log("내위치 좌표" + latitude, longitude);
    initializeMap(latitude, longitude);

});

function current() {
    navigator.geolocation.getCurrentPosition(function(position){
        // 현재 위치의 위도와 경도를 가져옴
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        console.log("내위치 좌표"+latitude, longitude);
        initializeMap(latitude, longitude);
        var geocoder = new kakao.maps.services.Geocoder();
        geocoder.coord2Address(longitude, latitude, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                // 주소를 얻어옴
                var address = result[0].address.address_name;
                var parts = address.split(" ");
                var extractedAddress = parts.slice(1, 3).join(" ");
                console.log("내 위치 좌표의 모든주소: " + address);
                console.log("내 위치 좌표의 주소: " + extractedAddress);
                $("#user_location").val(address); // 주소를 입력란에 채움
                $("#user_location2").val(extractedAddress); // 주소를 입력란에 채움
            }
        });
        marker.setPosition(map.getCenter());
    });
}


function initializeMap(latitude, longitude) {
    var mapContainer = document.getElementById('map');
    map = new kakao.maps.Map(mapContainer, {
        center: new kakao.maps.LatLng(latitude, longitude), // 초기 지도 중심 설정 (서울시청)
        level: 3
    });

    marker = new kakao.maps.Marker({
        position: map.getCenter(),
        map: map
    });

    // 지도의 중심 좌표가 변경될 때마다 마커의 위치를 재조정합니다.
    kakao.maps.event.addListener(map, 'center_changed', function() {
        var center = map.getCenter();

        // 좌표를 주소로 변환
        var geocoder = new kakao.maps.services.Geocoder();
        geocoder.coord2Address(center.getLng(), center.getLat(), function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                // 주소를 얻어옴
                var address = result[0].address.address_name;
                var parts = address.split(" ");
                var extractedAddress = parts.slice(1, 3).join(" ");
                console.log("현재 선택한 좌표의 모든주소: " + address);
                console.log("현재 선택한 좌표의 주소: " + extractedAddress);
                $("#user_location").val(address); // 주소를 입력란에 채움
                $("#user_location2").val(extractedAddress); // 주소를 입력란에 채움
            }
        });
        marker.setPosition(map.getCenter());
    });
}

window.onload = function (){
    current();
};