package ledger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Saves/loads transactions to a CSV file.
 *
 * Format:
 * id,timestamp,amount,description
 *
 * Notes:
 * - description is CSV-escaped (quotes doubled) and wrapped in quotes.
 * - amount uses BigDecimal string form.
 */
public final class CsvLedgerStore {

    public void save(Path file, List<Transaction> transactions) throws IOException {
        if (file == null) throw new IllegalArgumentException("file cannot be null");
        if (transactions == null) throw new IllegalArgumentException("transactions cannot be null");

        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }

        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            w.write("id,timestamp,amount,description");
            w.newLine();

            for (Transaction t : transactions) {
                String line = t.getId()
                        + "," + t.getTimestamp()
                        + "," + t.getAmount()
                        + "," + quoteCsv(t.getDescription());
                w.write(line);
                w.newLine();
            }
        }
    }

    public List<Transaction> load(Path file) throws IOException {
        if (file == null) throw new IllegalArgumentException("file cannot be null");
        if (!Files.exists(file)) return List.of();

        List<Transaction> out = new ArrayList<>();

        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String header = r.readLine(); // skip header
            if (header == null) return List.of();

            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                out.add(parseLine(line));
            }
        }

        return List.copyOf(out);
    }

    private Transaction parseLine(String line) {
        // We expect 4 fields: id, timestamp, amount, description
        // description may contain commas but is quoted.
        List<String> fields = splitCsvLine(line);
        if (fields.size() != 4) {
            throw new IllegalArgumentException("Bad CSV line (expected 4 fields): " + line);
        }

        UUID id = UUID.fromString(fields.get(0));
        LocalDateTime ts = LocalDateTime.parse(fields.get(1));
        BigDecimal amount = new BigDecimal(fields.get(2));
        String desc = fields.get(3);

        return Transaction.restore(id, amount, desc, ts);
    }

    private static String quoteCsv(String s) {
        String escaped = s.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private static List<String> splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    // double quote inside quoted field -> literal quote
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else if (c == '"') {
                    inQuotes = true;
                } else {
                    current.append(c);
                }
            }
        }

        fields.add(current.toString());
        return fields;
    }
}
