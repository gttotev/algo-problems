const std = @import("std");
const print = std.debug.print;

fn cmpNumStr(line: []u8, i: usize, str: []const u8) bool {
    return std.mem.eql(u8, line[i..@min(line.len, i + str.len)], str);
}

fn parseNumber(line: []u8, i: usize, c: u8) u8 {
    return switch (c) {
        '1'...'9' => c - '0',
        'o' => if (cmpNumStr(line, i, "one")) 1 else 0,
        't' => if (cmpNumStr(line, i, "two")) 2 else if (cmpNumStr(line, i, "three")) 3 else 0,
        'f' => if (cmpNumStr(line, i, "four")) 4 else if (cmpNumStr(line, i, "five")) 5 else 0,
        's' => if (cmpNumStr(line, i, "six")) 6 else if (cmpNumStr(line, i, "seven")) 7 else 0,
        'e' => if (cmpNumStr(line, i, "eight")) 8 else 0,
        'n' => if (cmpNumStr(line, i, "nine")) 9 else 0,
        else => 0,
    };
}

pub fn main() !void {
    var sum: u32 = 0;
    var buf: [100]u8 = undefined;
    const stdin = std.io.getStdIn().reader();
    while (try stdin.readUntilDelimiterOrEof(&buf, '\n')) |line| {
        // std.debug.print("{s}\n", .{line});
        for (0.., line) |i, c| {
            const res = parseNumber(line, i, c);
            if (res > 0) {
                sum += 10 * res;
                break;
            }
        }
        var i = line.len;
        while (i > 0) {
            i -= 1;
            const res = parseNumber(line, i, line[i]);
            if (res > 0) {
                sum += res;
                break;
            }
        }
    }
    std.debug.print("{}\n", .{sum});
}
