package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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

        Packet packet = new Packet();
        packet.globalStart = 0;
        packet.globalEnd = binary.length();
        packet.depth = 0;
        parsePacket(binary, packet);


        return sumPacketVersions(packet, new AtomicLong(0));
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    int parsePacket(String binary, Packet currentPacket) {

        var versionS = binary.substring(0, 3);
        var typeS = binary.substring(3, 6);

        var version = new BigInteger(versionS, 2).intValue();
        var type = new BigInteger(typeS, 2).intValue();

        currentPacket.binary = binary;
//        currentPacket.end = packetEnd;
        currentPacket.version = version;
        log.info("ver: {}", version);
        currentPacket.typeId = type;

        if (type == 4) {

            parseNumber(binary, currentPacket);
            log.info("parsing number, end at {}", currentPacket.end);
            return currentPacket.end;
        }

        var lengthTypeC = binary.charAt(6);

        if (lengthTypeC == '0') {

            return parseWithKnownLength(binary, currentPacket);

        } else {
            return parseWithKnownNumber(binary, currentPacket);
        }

    }

    private int parseWithKnownNumber(String binary, Packet currentPacket) {
        var pac = binary.substring(7, 7 + 11);
        var subPacketsCount = new BigInteger(pac, 2).intValue();

        int nextPacketStart = 7 + 11;

        while (currentPacket.subPackets.size() < subPacketsCount) {
            log.info("parsing with known number, {}/{}", currentPacket.subPackets.size(), subPacketsCount);

            var subPacketBinary = binary.substring(nextPacketStart);
            Packet subPacket = initPacket(currentPacket, nextPacketStart);

            currentPacket.subPackets.add(subPacket);
            var currentSubPacketLength = parsePacket(subPacketBinary, subPacket);
            nextPacketStart += currentSubPacketLength;

        }

        return getMaxEnd(currentPacket);
    }

    private int parseWithKnownLength(String binary, Packet currentPacket) {
        var pac = binary.substring(7, 7 + 15);
        var subPacketsLength = new BigInteger(pac, 2).intValue();

        int nextPacketStart = 7 + 15;
        int allPacketsEnd = nextPacketStart + subPacketsLength;

        while (nextPacketStart < allPacketsEnd) {
            log.info("parsing with known length, currently {}, end at {}", currentPacket.subPackets.size(), allPacketsEnd);

            var subPacketBinary = binary.substring(nextPacketStart, allPacketsEnd);
            Packet subPacket = initPacket(currentPacket, nextPacketStart);

            currentPacket.subPackets.add(subPacket);
            var currentSubPacketLength = parsePacket(subPacketBinary, subPacket);
            nextPacketStart += currentSubPacketLength;

        }
        return allPacketsEnd;
    }

    private Packet initPacket(Packet currentPacket, int nextPacketStart) {
        var subPacket = new Packet();
        subPacket.parent = currentPacket;
        subPacket.globalStart = nextPacketStart + currentPacket.globalStart;
        subPacket.depth = currentPacket.depth + 1;
        log.info("packet start at {}", subPacket.globalStart);
        return subPacket;
    }

    private void parseNumber(String binary, Packet currentPacket) {
        var chars = binary.toCharArray();
        int packetEnd = -1;
        StringBuilder numberBin = new StringBuilder();
        for (int i = 6; i < chars.length; i += 5) {

            numberBin.append(binary, i + 1, i + 5);
            if (chars[i] == '0') {
                packetEnd = i + 5;
                break;
            }
        }

//        String bin = binary.substring(7, packetEnd);
//        var val = new BigInteger(numberBin.toString(), 2);
//        val = null;

//        currentPacket.binary = binary.substring(0, packetEnd);
        currentPacket.end = packetEnd;
        currentPacket.globalEnd = currentPacket.globalStart + packetEnd;
    }

    private Long sumPacketVersions(Packet currentPacket, AtomicLong accum) {

        accum.addAndGet(currentPacket.version);
        for (Packet subPacket : currentPacket.subPackets) {
            sumPacketVersions(subPacket, accum);

        }

        return accum.get();
    }

    private int getMaxEnd(Packet packet) {

        if (packet.subPackets.isEmpty()) {
            return packet.globalEnd;
        } else {
            return getMaxEnd(packet.subPackets.get(packet.subPackets.size() - 1));
        }
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
        String binary;

        int globalStart;
        int globalEnd;
        int depth;

        int end;

        List<Packet> subPackets = new ArrayList<>();
        Packet parent;

    }
}
