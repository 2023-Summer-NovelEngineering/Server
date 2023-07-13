package dgsw.hs.kr.memo.controller;


import dgsw.hs.kr.memo.dto.response.ResponseData;
import dgsw.hs.kr.memo.annotation.CheckToken;
import dgsw.hs.kr.memo.dto.request.MemberLogInRequestDTO;
import dgsw.hs.kr.memo.dto.request.MemberSaveRequestDTO;
import dgsw.hs.kr.memo.dto.response.LoginResponse;
import dgsw.hs.kr.memo.dto.response.MemberInfoResponse;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController         // 해당 클래스가 컨트롤러임을 알리고 bean으로 등록하기 위함
@RequiredArgsConstructor    // 나중에 의존관계 관련하여 필요한 어노테이션
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseData<Member> createMember(@RequestBody MemberSaveRequestDTO memberSaveRequestDTO) {
        Member member = memberService.join(memberSaveRequestDTO);
        return new ResponseData<>(
                HttpStatus.CREATED,
                "회원가입 성공",
                member
        );
    }

    @PostMapping("/login")
    public ResponseData<LoginResponse> login(@RequestBody MemberLogInRequestDTO memberLogInRequestDTO){
        LoginResponse response = memberService.login(memberLogInRequestDTO);
        return new ResponseData<>(
                HttpStatus.OK,
                "로그인 성공",
                response
        );
    }

    //todo 내가 쓴 게시물, 하트 수
    @CheckToken
    @GetMapping("/my")
    public ResponseData<MemberInfoResponse> getMy(@RequestAttribute("member") Member member) {
        System.out.println("memberId : " + member.getMemberId());
        MemberInfoResponse response = memberService.getMy(member);
        return new ResponseData<>(
                HttpStatus.OK,
                "정보 주기 성공",
                response
        );
    }

    @CheckToken
    @GetMapping("/all/withBoard")
    public ResponseData<MemberInfoResponse> getAllWithBoard(@RequestAttribute("member") Member member) {
        MemberInfoResponse response = memberService.getAllWithBoard(member);
        return new ResponseData<>(
                HttpStatus.OK,
                "정보 주기 성공",
                response
        );
    }

}
