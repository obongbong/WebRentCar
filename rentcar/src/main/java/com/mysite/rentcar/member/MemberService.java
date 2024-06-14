package com.mysite.rentcar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberById(String memId) {
        return memberRepository.findById(memId);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMemberById(String memId) {
        memberRepository.deleteById(memId);
    }
}
