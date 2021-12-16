package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day16 extends Day {
    public static void main(String[] args) throws IOException {
        new Day16().printParts();
    }

    @Override
    public Object part1(String data) {

        var binary = new BigInteger(data, 16).toString(2);
        while (binary.length() % 4 != 0) {
            binary = "0" + binary;
        }

        AtomicInteger globalIndex = new AtomicInteger();
        List<Packet> stack = new ArrayList<>();

        Packet packet = new Packet();
        packet.depth = 0;
        stack.add(packet);
        parsePacket(binary, packet, globalIndex, stack);

        return sumPacketVersions(packet, new AtomicLong(0));
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    void parsePacket(String binary, Packet currentPacket, AtomicInteger globalIndex, List<Packet> stack) {

        int start = globalIndex.get();

        var versionS = binary.substring(start, start + 3);
        var typeS = binary.substring(start + 3, start + 6);

        var version = new BigInteger(versionS, 2).intValue();
        var type = new BigInteger(typeS, 2).intValue();

        currentPacket.version = version;
        currentPacket.typeId = type;

        if (type == 4) {
            parseNumber(binary, currentPacket, globalIndex);
            return;
        }

        var lengthTypeC = binary.charAt(start + 6);
        if (lengthTypeC == '0') {
            parseWithKnownLength(binary, currentPacket, globalIndex, stack);
        } else {
            parseWithKnownNumber(binary, currentPacket, globalIndex, stack);
        }

    }

    private void parseWithKnownNumber(String binary, Packet currentPacket, AtomicInteger globalIndex, List<Packet> stack) {

        int start = globalIndex.get();

        var pac = binary.substring(start + 7, start + 7 + 11);
        var subPacketsCount = new BigInteger(pac, 2).intValue();

        globalIndex.addAndGet(7 + 11);

        while (currentPacket.subPackets.size() < subPacketsCount) {
            log.info("parsing with known number, {}/{}", currentPacket.subPackets.size(), subPacketsCount);

            Packet subPacket = initPacket(currentPacket);
            currentPacket.subPackets.add(subPacket);
            stack.add(subPacket);
            parsePacket(binary, subPacket, globalIndex, stack);

        }
    }

    private void parseWithKnownLength(String binary, Packet currentPacket, AtomicInteger globalIndex, List<Packet> stack) {
        int start = globalIndex.get();

        var pac = binary.substring(start + 7, start + 7 + 15);
        var subPacketsLength = new BigInteger(pac, 2).intValue();

        int allPacketsEnd = globalIndex.addAndGet(7 + 15) + subPacketsLength;

        while (globalIndex.get() < allPacketsEnd) {
            log.info("parsing with known length, currently {}, end at {}", currentPacket.subPackets.size(), allPacketsEnd);

            Packet subPacket = initPacket(currentPacket);
            currentPacket.subPackets.add(subPacket);
            stack.add(subPacket);
            parsePacket(binary, subPacket, globalIndex, stack);

        }
    }

    private Packet initPacket(Packet currentPacket) {
        var subPacket = new Packet();
        subPacket.parent = currentPacket;
        subPacket.depth = currentPacket.depth + 1;
        return subPacket;
    }

    private void parseNumber(String binary, Packet currentPacket, AtomicInteger globalIndex) {

        StringBuilder numberBin = new StringBuilder();
        globalIndex.addAndGet(6);
        while (true) {
            var start = globalIndex.getAndAdd(5);
            numberBin.append(binary, start + 1, start + 5);
            if (binary.charAt(start) == '0')
                break;
        }
        currentPacket.number = new BigInteger(numberBin.toString(), 2).longValue();
    }

    private Long sumPacketVersions(Packet currentPacket, AtomicLong accum) {

        accum.addAndGet(currentPacket.version);
        for (Packet subPacket : currentPacket.subPackets) {
            sumPacketVersions(subPacket, accum);

        }

        return accum.get();
    }


    @Override
    public int getDayNumber() {
        return 16;
    }

    @Override
    public int getYear() {
        return 2021;
    }


    @ToString(exclude = "parent")
    private static class Packet {

        int version;
        int typeId;
        int depth;
        long number;

        List<Packet> subPackets = new ArrayList<>();
        Packet parent;

    }
}
