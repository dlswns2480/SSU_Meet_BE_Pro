package SSU.SSU_Meet_BE.Service;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Repository.StickyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StickyService {

    @Autowired private final StickyRepository stickyRepository;
    @Autowired private final MemberRepository memberRepository;

    /*
        1. 포스트잇 메인화면에 뿌리기
        2. 포스트잇 등록(저장)
        3. 포스트잇 구매 -> 코인 차감 후 멤버 저장, 구매했던 리스트에 구매한 포스트잇 추가
     */
    public List<StickyNote> findNotes(){
        return stickyRepository.findAll();
    }

    public void saveNotes(StickyNote stickyNote){
        stickyRepository.save(stickyNote);
    }

//    public void buyNotes(StickyNote stickyNote, Long memberId){
//        Optional<Member> member = memberRepository.findById(memberId);
//        if(member.get().getCoin() != null){
//            member.get().getCoin() -= 1;
//        }
//
//    }

}
