package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MarathonDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarathonService {
    private final MemberRepository memberRepository;
    private final MarathonRepository marathonRepository;
    private final MemberConverter memberConverter;
    private final MarathonConverter marathonConverter;

    public MarathonService(MemberRepository memberRepository,
            MarathonRepository marathonRepository,
            MemberConverter memberConverter,
            MarathonConverter marathonConverter) {
        this.memberRepository = memberRepository;
        this.marathonRepository = marathonRepository;
        this.memberConverter = memberConverter;
        this.marathonConverter = marathonConverter;
    }

    public List<MemberDTO> getAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOs = memberConverter.convertToDto(members);

        memberDTOs.forEach(dto -> {
            List<Marathon> marathons = marathonRepository.findByMemberId(dto.getId());
            dto.setMarathons(marathonConverter.convertToDto(marathons));
        });

        return memberDTOs;
    }

    public MemberDTO findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        MemberDTO memberDTO = memberConverter.convertToDto(member);
        List<Marathon> marathons = marathonRepository.findByMemberId(id);
        memberDTO.setMarathons(marathonConverter.convertToDto(marathons));

        return memberDTO;
    }

    @Transactional
    public MemberDTO updateMarathons(Long memberId, List<MarathonDTO> marathons) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        // Delete existing marathons
        marathonRepository.deleteByMember(member);

        // Save new marathons
        List<Marathon> marathonsToSave = marathons.stream()
                .map(marathonDTO -> {
                    Marathon marathon = marathonConverter.convertToEntity(marathonDTO);
                    marathon.setMember(member);
                    return marathon;
                })
                .toList();

        marathonRepository.saveAll(marathonsToSave);
        MemberDTO memberDTO = memberConverter.convertToDto(member);
        memberDTO.setMarathons(marathonConverter.convertToDto(marathonsToSave));
        return memberDTO;
    }

    @Transactional
    public MemberDTO addMarathons(Long memberId, List<MarathonDTO> marathons) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<Marathon> marathonsToSave = marathons.stream()
                .map(marathonDTO -> {
                    Marathon marathon = marathonConverter.convertToEntity(marathonDTO);
                    marathon.setMember(member);
                    return marathon;
                })
                .toList();
        marathonRepository.saveAll(marathonsToSave);

        List<Marathon> allMarathons = marathonRepository.findByMemberId(memberId);
        MemberDTO memberDTO = memberConverter.convertToDto(member);
        memberDTO.setMarathons(marathonConverter.convertToDto(allMarathons));
        return memberDTO;
    }

    @Transactional
    public MemberDTO updateMarathons(Long id, List<Marathon> marathons) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        marathonRepository.deleteByMember(entity);
        List<Marathon> marathonsToSave = marathons.stream()
                .map(marathon -> {
                    marathon.setMember(entity);
                    return marathon;
                })
                .toList();

        marathonRepository.saveAll(marathonsToSave);

        MemberDTO returnedDTO = memberConverter.convertToDto(entity);
        returnedDTO.setMarathons(marathonConverter.convertToDto(marathonsToSave));
        return returnedDTO;
    }

    @Transactional
    public MemberDTO addMarathons(Long id, List<Marathon> marathons) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        List<Marathon> marathonsToSave = marathons.stream()
                .map(marathon -> {
                    marathon.setMember(entity);
                    return marathon;
                })
                .toList();

        marathonRepository.saveAll(marathonsToSave);

        List<Marathon> allMarathons = marathonRepository.findByMemberId(id);
        MemberDTO returnedDTO = memberConverter.convertToDto(entity);
        returnedDTO.setMarathons(marathonConverter.convertToDto(allMarathons));
        return returnedDTO;
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        marathonRepository.deleteByMember(member);
        memberRepository.deleteById(id);
    }
}