board_list(   0  , "" , "" );       //  cno , page , key , keyword

let current_page = 0;
let current_key = "bname"; // 현재 검색된 키 변수  [ 없을경우 공백 ]
let current_keyword = ""; // 현재 검색된 키워드 변수[ 없을경우 공백 ]
let index_data=0;
// $(document).ready(function(){
//     $("#boarddiv").hide();
//
// })

let aa=0;
let bb="";
let cc="";
let dd="";
let ee="";
function board_list( page , key , keyword   ){


        this.current_page = page;
        if( key != undefined ) { this.current_key = key; }
        if( keyword != undefined ){ this.current_keyword = keyword; }


//        alert( "현재 페이지번호 : " + this.current_page  );
//        alert( "현재 key  : " + this.current_key  );
//        alert( "현재 keyword : " + this.current_keyword  );

        $.ajax({
            url : "/board/getboardlist" ,
            data : { "key" :  this.current_key  , "keyword" : this.current_keyword , "page" :  this.current_page } ,
            contentType: "application/json; charset=UTF-8",

            method : "GET",
            success : function( boardlist ){

//                console.log( boardlist );

//                console.log( boardlist.data.length );
//                console.log(typeof 'boardlist');

                 let html = '<tr>'
                                + '<th width="55%">번호</th>'
                                + '<th width="45%">이름</th>'
                                + '</tr>';

                if( boardlist.data.length == 0 ){ // 검색 결과가 존재하지 않으면
//
//
//                        if (!confirm("확인(예) 또는 취소(아니오)를 선택해주세요.")) {
//                                    alert("취소(아니오)를 누르셨습니다.");
//                                } else {
//                                    alert("확인(예)을 누르셨습니다.");
//                                }
                          html +=
//
//
                                '<tr>'+
                                        '<td colspan="5"> 정보가 존재 하지 않습니다.</td> '+

                                 '</tr>';


                }else{


                        for( let i = 0 ; i<boardlist.data.length ; i++ ){
//                             alert( "현재 i : "+ i );
                            html +=
                                    '<tr>'+
                                          '<td>'+boardlist.data[i].bno+'</td> '+

                                          '<td onclick="abcmart('+(boardlist.data[i].bno)+')"'+(boardlist.data[i].bno)+'> '+boardlist.data[i].bname+'</td> '+



                                  + '</tr>';
                        }
                 }


//////////////////////////////////////////////////////////////////////////////////////// 페이징 버튼 생성 코드 ///////////////////////////////////////////////////////////////////////
                 let pagehtml = "";
                 ////////////////////////////////////////////  이전 버튼 ////////////////////////////////////////////////
                 if( page == 0 ){   // 현재 페이지가 첫페이지 이면
                        pagehtml +=
                         '<li class="page-item"> '+
                                     '<button class="page-link" onclick="board_list('+ (page)  +')"> 이전 </button>'+  // 검색 없음
                          '</li>';
                 }else{  // 현재 페이지가 첫페이지가 아니면
                     pagehtml +=
                        '<li class="page-item"> '+
                                    '<button class="page-link" onclick="board_list('+ (page-1)  +')"> 이전 </button>'+  // 검색 없음
                         '</li>';
                  }
                 ////////////////////////////////////////////  ////////////////////////////////// ////////////////////////////////////////////////
                ////////////////////////////////////////// 가운데에 들어가는 페이징 버튼수 //////////////////////////////////////////
                 for( let i = boardlist.startbtn ; i<=boardlist.endhtn ; i++ ){
                    pagehtml +=
                          '<li class="page-item"> '+
                            '<button class="page-link" onclick="board_list('+(i-1)+')"> '+i+' </button>'+  // 검색 없음
                          '</li>';
                 }
                ///////////////////////////////////////// ///////////////////////////////////////  //////////////////////////////////////////
                ////////////////////////////////////////////  다음 버튼 ////////////////////////////////////////////////
                if( page == boardlist.totalpages -1 ){ // 현재 페이지가 마지막 페이지이면
                     pagehtml +=
                            '<li class="page-item"> '+
                                        '<button class="page-link" onclick="board_list('+ (page)  +')"> 다음 </button>'+  // 검색 없음
                             '</li>';
                }else{ // 아니면
                     pagehtml +=
                        '<li class="page-item"> '+
                                    '<button class="page-link" onclick="board_list('+ (page+1)  +')"> 다음 </button>'+  // 검색 없음
                         '</li>';
                }

                ////////////////////////////////////////////  ////////////// ////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////  ///////////////////////////////////////////////////////////////////////

                $("#boardtable").html( html ); // 테이블에 html  넣기
                $("#pagebtnbox").html( pagehtml); // 페이징버튼 html 넣기



            }
        });

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            $(document).ready(function(){
                     $("#boarddiv").hide();

                 })

                 $(function() {

                     $('#boardtable').on("click", function() {
                         // 모든 <button> 요소가 처음 클릭됐을 때에만 실행
                         $("#boarddiv").show();

                     });
                 });
//
function boarddiv_close(){
           $("#boarddiv").hide();
    }

function search_name(){
    let key = $("#key").val();
    let keyword = $("#keyword").val();
    // 키 와 키워드 입력받음
    board_list(  0 ,  key , keyword );

}
// 3. U 수정 처리 메소드
function board_update(a,b,c,d,e){
        alert("a :"+a)
        alert("b :"+b)
        alert("c :"+c)
        alert("d :"+d)
        alert("e :"+e)


//        let form1 = $("#updateform")[0];
//        let form = $("#updateform");

        let formData_update = new FormData( );
        formData_update.append("bno",a)
        formData_update.append("bname",b)
        formData_update.append("baddress",c)
        formData_update.append("bacademy",d)
        formData_update.append("bmajor",e)
         alert("json form1"+JSON.stringify(formData_update));
//         console.log(FormData.get());
         alert("FormData")
         alert("FormDataFormData:"+formData_update)
//        for (var key of updateform.keys()) {
//        //
//                                      console.log(key);
//        //
//         }
//         for (var value of updateform.values()) {
//
//                                       console.log(value);
//
//                                     }

        $.ajax({
//
            url : "/board/update" ,
            data : {"bno":a,"bname":b,"baddress":c,"bacademy":d,"bmajor":e} ,
            method : "PUT",
            processData : false ,
            contentType : false ,
            success : function( re ){
            alert( "re" );
                alert( re );
            }
        });
}




function board_get(bno){

     let formData = new FormData();

    $.ajax({
        url: '/board/getboard' ,
        data:{"bno":bno},
        success : function( board ){





            let html =
                        '<div>'+
//                        '<form id="updateform">'+
                           ' 번호 <input type="text" id="get_bno" readonly value="'+board.bno+'"><br>' +
                            '성명 <input type="text" id="get_name" readonly value="'+board.bname+'"><br>' +
                            '주소 <input type="text" id="get_add" readonly value="'+board.baddress+'"><br>' +
                            '학교 <input type="text" id="get_aca" readonly value="'+board.bacademy+'"><br>' +
                            '전공 <input type="text" id="get_major" readonly value="'+board.bmajor+'"><br>' +
                            '<br>'+
                            '<button onclick="boarddiv_close()"> 닫기 </button>'+
//                            '<button onclick="board_update('+board.bno+')"> 수정 </button>'+
                            '<button onclick="window_update('+board.bno+','+"bb"+','+"cc"+','+"dd"+','+"ee"+')"> 수정 </button>'+
                            '<button onclick="board_delete('+board.bno+')"> 삭제 </button>';
//                        '</form>'+
                      + '</div>';

            $("#boarddiv").html(html);

        }

    });

}

function window_update(a,b,c,d,e){

    let update_bno0 = a
    let child;
    child= window.open("board/update","수정하기","width=350,height=400,resizable=no")

}


function board_delete( bno ){
      $.ajax({
         url : "/board/delete" ,
         data : { "bno" : bno } ,
         method : "DELETE",
         success : function( re ){
            alert( "삭제 완료" );
            location.reload();
         }
     });
}


 function abcmart(index_data){

       board_get(index_data);


}



// 1. C 쓰기 처리 메소드
function board_save(){
//    let form = $("#saveform2")[0];
    let form = $("#saveform")[0];
    let formdata = new FormData( form );

    $.ajax({
        url : "/board/save" ,
        data : formdata ,
        method : "POST",
        processData: false,
        contentType: false,
//        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success : function( re ){

            if( re == true ){
                    alert("입력 완료");;
                    opener.location.reload();

                    window.close();
            }else{
                    alert("[작성실패] 관리자에게 문의하세요");
            }

         }

    });

}

function save_close(){

    window.close();
}

function refresh(){
    alert("referesh")
    $("#boardtable").load("$board/save #boardtable")
//    $("#boardlist").load(window.location.href +"#boardlist")
}
