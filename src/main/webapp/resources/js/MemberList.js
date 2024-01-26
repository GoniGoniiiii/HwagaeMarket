    document.addEventListener('DOMContentLoaded', function () {
        var searchTypeSelect = document.querySelector('select[name="searchType"]');
        var searchKeyInput = document.querySelector('input[name="searchKey"]');

        searchTypeSelect.addEventListener('change', function () {
            if (this.value === 'tel') {
                searchKeyInput.placeholder = "'-'를 포함하여 모두 입력하세요.";
            }
        });
        searchTypeSelect.addEventListener('change', function () {
            if (this.value === 'name') {
                searchKeyInput.placeholder = "이름을 입력하세요.";
            }
        });
        
    });