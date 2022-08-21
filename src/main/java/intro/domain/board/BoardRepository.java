package intro.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {

    @Query( value = "select * from info_table where bname like %:keyword% order by bname desc" , nativeQuery = true )
    Page<BoardEntity> findBybname( @Param("keyword") String keyword , Pageable pageable);
    // List 대신 Page 사용하는이유 : Page 관련된 메소드를 사용하기 위해

    //

//    @Query( value = "select * from info_table where bno like %:keyword% ", nativeQuery = true  )
    @Query( value = "select * from info_table where bno like %:keyword% ", nativeQuery = true  )
            //select * from board where cno = :cno and btitle like %:keyword%
//    @Query( value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true  )
    Page<BoardEntity> findBybno(  @Param("keyword") String keyword , Pageable pageable  );


//    // 2. 내용 검색
//    @Query( value = "select * from info_table where bno like %:keyword%" , nativeQuery = true )
//    Page<BoardEntity> findBybno( @Param("keyword") String keyword , Pageable pageable  );
////
//    // 3. 작성자 검색
//    @Query( value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true  )
//    Page<BoardEntity> findBymno(   int cno ,    @Param("memberEntity") MemberEntity memberEntity , Pageable pageable  );
}
