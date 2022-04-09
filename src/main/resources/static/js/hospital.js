// sgguNm

var getSgguNm = async function (selectSidoNm) {
    var response = await fetch(`http://localhost:8765/api/sgguNm?sidoNm=${selectSidoNm}`);
    var responseParsing = await response.json();
    setSgguNm(responseParsing);
}
var setSgguNm = function(response) {
    var sgguNm = document.querySelector("#sgguNm");
    // 목록 초기화하기
    sgguNm.innerHTML = "";
    // 받아온 데이터 하나씩 추가하기
    response.forEach( (e) => { 
        var optionEL = document.createElement("option");
        optionEL.text = e;
        sgguNm.append(optionEL);
    });
}

 // init 
var sidoNm = document.querySelector("#sidoNm");
var init = new function(){
    var selectSidoNm = sidoNm.value;
    getSgguNm(selectSidoNm);
};
sidoNm.addEventListener("change", function(e) {
    var selectSidoNm = e.target.value;
    // 백틱 숫자 옆에 있는 ` 사용하면 자바스크립트 변수 바인딩 가능
    getSgguNm(selectSidoNm);
});

// hospital
var getHospital = async function(sidoNm,sgguNm) {
    
    var response = await fetch(`http://localhost:8765/api/hospital?sidoNm=${sidoNm}&sgguNm=${sgguNm}`);
    var responseParsing = await response.json();
    setHospital(responseParsing);

    sidoNm.text = document.querySelector("#sidoNm").text;
}
var setHospital = function(response) {
    var tbody = document.querySelector("#tbody-hospital");
    tbody.innerHTML ="";
    
    response.forEach( (e) => { 
        var trEL = document.createElement("tr");
        tbody.append(trEL);
        var tdEL1 = document.createElement("td");
        var tdEL2 = document.createElement("td");
        var tdEL3 = document.createElement("td");
        tdEL1.innerHTML = e.yadmNm;
        tdEL2.innerHTML = e.spclAdmTyCd;
        tdEL3.innerHTML = e.telno;
        trEL.append(tdEL1);
        trEL.append(tdEL2);
        trEL.append(tdEL3);
        
    });
}
document.querySelector("#btn-submit").addEventListener("click", function(e){
    var sgguNm = document.querySelector("#sgguNm").value;
    var sidoNm = document.querySelector("#sidoNm").value;
    getHospital(sidoNm,sgguNm);
})
