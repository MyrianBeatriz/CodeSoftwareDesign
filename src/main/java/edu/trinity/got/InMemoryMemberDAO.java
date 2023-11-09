package edu.trinity.got;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMemberDAO implements MemberDAO {

    private final Collection<Member> allMembers =
            MemberDB.getInstance().getAllMembers();

    @Override
    public Optional<Member> findById(Long id) {
        return allMembers.stream()
                .filter(member -> member.id().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return allMembers.stream()
                .filter(member -> member.name().equals(name))
                .findFirst();
    }

    @Override
    public List<Member> findAllByHouse(House house) {
        return allMembers.stream()
                .filter(member -> member.house() == house)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Member> getAll() {
        return allMembers;
    }

    @Override
    public List<Member> startWithSandSortAlphabetically() {
        return allMembers.stream()
                .filter(member -> member.name().startsWith("S"))
                .sorted(Comparator.comparing(Member::name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> lannisters_alphabeticallyByName() {
        return allMembers.stream()
                .filter(member -> member.house() == House.LANNISTER)
                .sorted(Comparator.comparing(Member::name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> salaryLessThanAndSortByHouse(double max) {
        return allMembers.stream()
                .filter(member -> member.salary() < max)
                .sorted(Comparator.comparing(Member::house))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> sortByHouseNameThenSortByNameDesc() {
        return allMembers.stream()
                .sorted(Comparator.comparing(Member::house)
                        .thenComparing(Comparator.comparing(Member::name).reversed()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> houseByDob(House house) {
        return allMembers.stream()
                .filter(member -> member.house() == house)
                .sorted(Comparator.comparing(Member::dob))
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> kingsByNameDesc() {
        return allMembers.stream()
                .filter(member -> member.title() == Title.KING)
                .sorted(Comparator.comparing(Member::name).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public double averageSalary() {
        return allMembers.stream()
                .mapToDouble(Member::salary)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<String> namesSorted(House house) {
        return allMembers.stream()
                .filter(member -> member.house() == house)
                .map(Member::name)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean salariesGreaterThan(double max) {
        return allMembers.stream()
                .anyMatch(member -> member.salary() > max);
    }

    @Override
    public boolean anyMembers(House house) {
        return allMembers.stream()
                .anyMatch(member -> member.house() == house);
    }

    @Override
    public long howMany(House house) {
        return allMembers.stream()
                .filter(member -> member.house() == house)
                .count();
    }

    @Override
    public String houseMemberNames(House house) {
        return allMembers.stream()
                .filter(member -> member.house() == house)
                .map(Member::name)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    @Override
    public Optional<Member> highestSalary() {
        return allMembers.stream()
                .max(Comparator.comparing(Member::salary));
    }

    @Override
    public Map<Boolean, List<Member>> royaltyPartition() {
        return allMembers.stream()
                .collect(Collectors.partitioningBy(member ->
                        member.title() == Title.KING || member.title() == Title.QUEEN));
    }

    @Override
    public Map<House, List<Member>> membersByHouse() {
        return allMembers.stream()
                .collect(Collectors.groupingBy(Member::house));
    }

    @Override
    public Map<House, Long> numberOfMembersByHouse() {
        return allMembers.stream()
                .collect(Collectors.groupingBy(Member::house, Collectors.counting()));
    }

    @Override
    public Map<House, DoubleSummaryStatistics> houseStats() {
        return allMembers.stream()
                .collect(Collectors.groupingBy(Member::house,
                        Collectors.summarizingDouble(Member::salary)));
    }

}

