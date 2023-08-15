    // 검색어 아마 팀에서 활용할 때는 주소명 장소명으로 명확하게.
    var keyword = ['종로3가 중앙hta']
	
	// 지도를 표시할 div
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567),
            level: 3
        };
    
    // 지호 수정 부분 
    // 지도를 생성한다.
    var map = new kakao.maps.Map(mapContainer, mapOption);
    // 지도를 생성하고 드래그와 줌, 더블클릭 기능을 막는다.
    kakao.maps.event.addListener(map, 'dblclick', function(mouseEvent) {
    	mouseEvent.preventDefault();
	});
    map.setDraggable(false);
    map.setZoomable(false);
  	
    var ps = new kakao.maps.services.Places();
    var bounds = new kakao.maps.LatLngBounds();
 	
	// 키워드로 검색한다.
    keywordSearch(keyword);
    function keywordSearch(keyword) {
        ps.keywordSearch(keyword, placesSearchCB);
    }
	
	// 지호 수정 부분
	// 무조건 검색 기록 중 첫번째 데이터로 조회
	// Map div를 클릭하면 상세페이지가 있는 카카오맵 새 창을 연다.
    function placesSearchCB(data, status) {
        if (status === kakao.maps.services.Status.OK) {
            handleMapClick(data[0]);
            //displayMarker(data[0]);
            bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x));
            setBounds();
        }
    }
    
	// 지도 클릭 이벤트를 처리하는 함수
	function handleMapClick(place) {
		var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(place.y, place.x),
        });
        document.getElementById('map').addEventListener('click', function() {
	        var url = 'https://map.kakao.com/link/map/' + place.id;
    	    window.open(url, '_blank');
		})
		return place;
	//	console.log(place);
	};
	
    function setBounds() {
        map.setBounds(bounds, 90, 30, 10, 30);
    }
/*    function displayMarker(place) {
        var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(place.y, place.x),
        });
        kakao.maps.event.addListener(marker, 'click', function () {
            var position = this.getPosition();
            var url = 'https://map.kakao.com/link/map/' + place.id;
            window.open(url, '_blank');
        });
    }*/

    
	
