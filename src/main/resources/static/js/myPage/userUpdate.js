// 회원 탈퇴 확인 함수
function confirmed() {
    // 이벤트 기본 동작 중단
    event.preventDefault();
    // 사용자에게 확인 메시지 표시
    const userResponse = window.confirm("회원탈퇴 페이지로 이동할까요?");
    // 사용자가 확인을 선택한 경우
    if (userResponse) {
        // 사용자의 닉네임을 가져옴
        const userNick = "${session.user.user_nick}";
        // 회원 탈퇴 페이지로 이동
        window.location.href = "/myPage/unRegister/" + userNick;
    } else {
        // 사용자가 취소를 선택한 경우 아무 동작도 하지 않음
    }
}

// URL에서 success 매개변수를 확인하여 업데이트 성공 여부를 판단
const urlParams = new URLSearchParams(window.location.search);
const success = urlParams.get('success');

// 업데이트 성공 시 알림창 띄우기
if (success === 'true') {
    alert('정보가 수정되었습니다.');
}

// 비밀번호 확인 함수
function validatePassword() {
    // 비밀번호와 확인 비밀번호를 가져옴
    var password = document.getElementById("user_pw").value;
    var confirmPassword = document.getElementById("confirm_password").value;

    // 비밀번호가 일치하지 않으면 알림 표시 후 false 반환
    if (password != confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }
    // 일치하면 true 반환
    return true;
}

// 닉네임 중복 확인 함수
const nickCheck = () => {
    // 입력된 닉네임과 결과를 표시할 요소를 가져옴
    const user_nick = document.getElementById("user_nick").value;
    const checkResult = document.getElementById("check-result");

    // 입력값이 없는 경우 경고 표시
    if (user_nick.trim() === "") {
        checkResult.innerHTML = "내용을 입력하세요.";
        return;
    }

    console.log("입력값: ", user_nick);
    // 서버로 닉네임 중복 확인 요청을 보냄
    $.ajax({
        type: "post",
        url: "/user/nick-check",
        data: {
            "user_nick" : user_nick
        },
        success: function (res){
            console.log("요청성공",res);
            // 사용 가능한 닉네임인 경우 표시
            if (res == "ok"){
                console.log("사용 가능한 닉네임");
                checkResult.style.color = "blue"
                checkResult.innerHTML = "사용 가능한 닉네임입니다.";
            } else {
                // 이미 사용 중인 경우 표시
                console.log("이미 사용중인 닉네임입니다");
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 닉네임입니다.";
            }
        },
        error: function (err){
            // 에러 발생 시 로그 표시
            console.log("에러발생",err);
        }
    });
}



// 지도 관련 함수
var map; // 지도 객체를 저장하는 변수
var marker; // 마커 객체를 저장하는 변수

// 주소 검색 API를 호출하는 함수
function openZipSearch() {
    // 다음 우편번호 서비스 객체 생성
    new daum.Postcode({
        oncomplete: function(data) {
            // 검색 결과에 따라 주소를 설정
            var addr;
            if (data.jibunAddress) {
                addr = data.jibunAddress;
            } else if (data.autoJibunAddress) {
                addr = data.autoJibunAddress;
            } else {
                addr = "주소 정보를 찾을 수 없습니다.";
            }
            // 입력된 주소를 입력란에 설정
            var addr2 = data.sigungu + " " + data.bname;
            console.log("검색된 지번주소:" +addr);
            console.log("검색된 구+동 주소:" +addr2);

            $("#user_location").val(addr);

            // 주소 검색 함수 호출
            searchAddress();
        }
    }).open();
}

// 주소를 검색하여 지도에 표시하는 함수
function searchAddress() {
    // 입력된 주소 가져오기
    var addr = document.getElementById('user_location').value;
    // 주소 검색 객체 생성
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소 검색 요청
    geocoder.addressSearch(addr, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            // 검색 결과 좌표로 지도 이동
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            map.setCenter(coords); // 지도 중심 설정

            // 마커 설정
            if(marker) {
                marker.setPosition(coords);
            } else {
                marker = new kakao.maps.Marker({
                    position: coords,
                    map: map
                });
            }
        } else {
            alert('검색에 실패하였습니다.');
        }
    });
}

// 현재 위치를 표시하는 함수
navigator.geolocation.getCurrentPosition(function(position){
    var latitude = position.coords.latitude; // 위도
    var longitude = position.coords.longitude; // 경도
    console.log("내위치 좌표" + latitude, longitude);
    initializeMap(latitude, longitude);
});

// 현재 위치를 찾는 함수
function current() {
    // 현재 위치 정보 가져오기
    navigator.geolocation.getCurrentPosition(function(position){
        var latitude = position.coords.latitude; // 위도
        var longitude = position.coords.longitude; // 경도
        console.log("내위치 좌표"+latitude, longitude);

        // 입력된 주소가 있는지 확인
        var userLocation = $("#user_location").val().trim();
        if (userLocation === "") {
            // 입력된 주소가 없으면 지도 초기화 함수 호출
            initializeMap(latitude, longitude);
            return;
        }

        // 입력된 주소가 있으면 현재 위치의 주소로 설정하고 지도에 표시
        var geocoder = new kakao.maps.services.Geocoder();
        // 현재 위치 좌표로 주소 변환 요청
        geocoder.coord2Address(longitude, latitude, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                // 변환된 주소 가져오기
                var address = result[0].address.address_name;
                var parts = address.split(" ");
                var extractedAddress = parts.slice(1, 3).join(" ");
                console.log("내 위치 좌표의 모든주소: " + address);
                console.log("내 위치 좌표의 주소: " + extractedAddress);

                // 주소 입력란에 값 설정
                $("#user_location").val(address);
                $("#user_location2").val(extractedAddress);

                // 지도 초기화 함수 호출
                initializeMap(latitude, longitude);
            }
        });
    });
}

// 지도 초기화 및 마커 표시 함수
function initializeMap() {
    var addr = document.getElementById('user_location').value; // 입력된 주소 가져오기
    var geocoder = new kakao.maps.services.Geocoder(); // 주소 검색 객체 생성

    // 주소 검색 요청
    geocoder.addressSearch(addr, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            // 검색된 주소의 좌표로 지도 초기화
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도 옵션 설정
            var mapContainer = document.getElementById('map');
            var mapOptions = {
                center: coords, // 지도 중심 좌표 설정
                level: 3 // 지도 확대 수준 설정
            };
            // 지도 객체 생성
            map = new kakao.maps.Map(mapContainer, mapOptions);

            // 새로운 마커 생성
            marker = new kakao.maps.Marker({
                position: coords,
                map: map
            });

            // 지도 중심 변경 시 이벤트 리스너 등록
            kakao.maps.event.addListener(map, 'center_changed', function() {
                var center = map.getCenter(); // 변경된 중심 좌표 가져오기

                // 변경된 중심 좌표로 주소 변환 요청
                geocoder.coord2Address(center.getLng(), center.getLat(), function(result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        // 변환된 주소 가져오기
                        var address = result[0].address.address_name;
                        var parts = address.split(" ");
                        var extractedAddress = parts.slice(1, 3).join(" ");
                        console.log("현재 선택한 좌표의 모든주소: " + address);
                        console.log("현재 선택한 좌표의 주소: " + extractedAddress);

                        // 주소 입력란에 값 설정
                        $("#user_location").val(address);
                        $("#user_location2").val(extractedAddress);
                    }
                });

                // 마커 위치 변경
                marker.setPosition(map.getCenter());
            });
        }
    });
}
