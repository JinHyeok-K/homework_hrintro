
board_get();
// 1. 특정 게시물 호출
function board_get(){
    $.ajax({
        url: '/board/getboard' ,
        success : function( board ){
            let html =
                           ' <div>게시물번호 : '+board.bno+'</div>'+
                            '<div>성명 : '+board.bname+' </div>'+
                            '<button onclick="board_delete('+board.bno+')"> 삭제 </button>';
            $("#boarddiv").html(html);
        }
    });
}
// 3. D 삭제 처리 메소드
function board_delete( bno ){
      $.ajax({
         url : "/board/delete" ,
         data : { "bno" : bno } ,
         method : "DELETE",
         success : function( re ){
            alert( re );
         }
     });
}