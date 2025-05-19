package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.converter.MarathonConverter;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
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
    public MemberDTO save(Long id, MemberDTO dto) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        marathonRepository.deleteByMember(entity);

        Member updatedMember = memberConverter.convertToEntity(dto, entity);
        Member savedMember = memberRepository.save(updatedMember);
        List<Marathon> marathonsToSave = dto.getMarathons()
                .stream()
                .map(marathonDTO -> {
                    Marathon marathon = marathonConverter.convertToEntity(marathonDTO);
                    marathon.setMember(savedMember);
                    return marathon;
                })
                .toList();

        marathonRepository.saveAll(marathonsToSave);

        MemberDTO returnedDTO = memberConverter.convertToDto(savedMember);
        returnedDTO.setMarathons(marathonConverter.convertToDto(marathonsToSave));
        return returnedDTO;
    }

    public MemberDTO save(MemberDTO dto) {
        Member memberToSave = memberConverter.convertToEntity(dto);
        Member savedMember = memberRepository.save(memberToSave);
        List<Marathon> marathonsToSave = dto.getMarathons()
                .stream()
                .map(marathonDTO -> {
                    Marathon marathon = marathonConverter.convertToEntity(marathonDTO);
                    marathon.setMember(savedMember);
                    return marathon;
                })
                .toList();

        marathonRepository.saveAll(marathonsToSave);

        MemberDTO returnedDTO = memberConverter.convertToDto(savedMember);
        returnedDTO.setMarathons(marathonConverter.convertToDto(marathonsToSave));
        return returnedDTO;
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo com id " + id + " não encontrado"));

        marathonRepository.deleteByMember(member);
        memberRepository.deleteById(id);
    }
}