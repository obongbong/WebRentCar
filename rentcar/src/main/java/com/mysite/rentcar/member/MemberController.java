package com.mysite.rentcar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String showMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "member_form";
    }

    @PostMapping
    public String submitMemberForm(@ModelAttribute Member member) {
        memberService.saveMember(member);
        return "redirect:/members/list"; // 등록 후 회원 리스트 페이지로 리다이렉트
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Member> memberList = memberService.findAllMembers();
        model.addAttribute("memberList", memberList);
        return "member_list";
    }

    @GetMapping // 추가된 부분
    public String redirectToNewMemberForm() {
        return "redirect:/members/new"; // GET 요청을 /members/new로 리다이렉트
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam(name = "memId", required = false) String memId, Model model) {
        if (memId == null || memId.isEmpty()) {
            List<Member> memberList = memberService.findAllMembers();
            model.addAttribute("memberList", memberList);
        } else {
            Optional<Member> memberOptional = memberService.findMemberById(memId);
            if (memberOptional.isPresent()) {
                model.addAttribute("memberList", List.of(memberOptional.get()));
            } else {
                model.addAttribute("memberList", List.of());
            }
        }
        return "member_list";
    }
    
    @GetMapping("/edit/{memId}")
    public String showEditForm(@PathVariable("memId") String memId, Model model) {
        Optional<Member> memberOptional = memberService.findMemberById(memId);
        memberOptional.ifPresent(member -> model.addAttribute("member", member));
        return "member_edit";
    }

    @PostMapping("/update")
    public String updateMember(@ModelAttribute Member member) {
        memberService.saveMember(member);
        return "redirect:/members/list";
    }
    
    @PostMapping("/delete/{memId}")
    public String deleteMember(@PathVariable("memId") String memId, RedirectAttributes redirectAttributes) {
        Optional<Member> memberOptional = memberService.findMemberById(memId);
        if (memberOptional.isPresent()) {
            memberService.deleteMemberById(memId);
            redirectAttributes.addFlashAttribute("successMessage", "회원을 삭제했습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "회원을 찾을 수 없습니다.");
        }
        return "redirect:/members/list";
    }
}
