


let p_id = opener.$("#get_bno").val(); //부모창에서 id가 parent인 태그의 val()
let p_name = opener.$("#get_name").val(); //부모창에서 id가 parent인 태그의 val()
let p_add = opener.$("#get_add").val(); //부모창에서 id가 parent인 태그의 val()
let p_aca = opener.$("#get_aca").val(); //부모창에서 id가 parent인 태그의 val()
let p_major = opener.$("#get_major").val(); //부모창에서 id가 parent인 태그의 val()

printName(p_id,p_name,p_add,p_aca,p_major)
function printName(a,b,c,d,e)  {
    let bno = a;
    let bname = b;
    let badd = c;
    let baca = d;
    let bmajor = e;

   $(document).ready(function(){

      $("#bno").val(bno);
      $("#bname").val(bname);
      $("#baddress").val(badd);
      $("#bacademy").val(baca);
      $("#bmajor").val(bmajor);
   })

 }


// 3. U 수정 처리 메소드
function board_update(){
        let form = $("#updateform")[0];
        let formdata = new FormData( form );
        $.ajax({
            url : "/board/update" ,
            data : formdata ,
            method : "PUT",
            processData : false ,
            contentType : false ,
            success : function( re ){
                alert( "수정이 되었습니다" );
                opener.location.reload();
                window.close()
            }
        });
}




function update_close(){

    window.close();
}