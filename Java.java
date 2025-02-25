import java.util.*;

public class Java {
    public static void main(String[] args) {
        // สร้าง List nodes และทำการ Map key, value ตาม rawdata ที่ให้มาเพิ่มลงไปใน List
        List<Map<String, String>> nodes = new ArrayList<>();
        nodes.add(Map.of("id", "input-node-1", "type", "input"));
        nodes.add(Map.of("id", "voyage-embed-node-2", "type", "org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle"));
        nodes.add(Map.of("id", "voyage-transform-node-3", "type", "org.maoz.prehandle.workers.neoai.aiclient.embedding.util.VoyageTransformVerticle"));
        nodes.add(Map.of("id", "http-client-adapter-verticle-node-4", "type", "org.maoz.prehandle.workers.neoai.httpclient.HttpClientAdapterVerticle"));
        nodes.add(Map.of("id", "line-node-7", "type", "org.maoz.prehandle.workers.neoai.notify.LineVerticle"));
        nodes.add(Map.of("id", "facebook-node-8", "type", "org.maoz.prehandle.workers.neoai.notify.FacebookVerticle"));
        nodes.add(Map.of("id", "discord-node-9", "type", "org.maoz.prehandle.workers.neoai.notify.DiscordVerticle"));
        nodes.add(Map.of("id", "to-publish-verticle-node-10", "type", "org.maoz.prehandle.workers.neoai.ebtransform.ToPublishVerticle"));
        nodes.add(Map.of("id", "output-node-10", "type", "org.maoz.prehandle.workers.neoai.output.OutputVerticle"));

         // สร้าง List edges และทำการ Map key, value ตาม rawdata ที่ให้มาเพิ่มลงไปใน List
        List<Map<String, String>> edges = new ArrayList<>();
        edges.add(Map.of("source", "input-node-1", "target", "voyage-embed-node-2"));
        edges.add(Map.of("source", "voyage-embed-node-2", "target", "http-client-adapter-verticle-node-4"));
        edges.add(Map.of("source", "http-client-adapter-verticle-node-4", "target", "voyage-transform-node-3"));
        edges.add(Map.of("source", "voyage-transform-node-3", "target", "to-publish-verticle-node-10"));
        edges.add(Map.of("source", "to-publish-verticle-node-10", "target", "line-node-7"));
        edges.add(Map.of("source", "to-publish-verticle-node-10", "target", "facebook-node-8"));
        edges.add(Map.of("source", "to-publish-verticle-node-10", "target", "discord-node-9"));
        edges.add(Map.of("source", "to-publish-verticle-node-10", "target", "output-node-10"));

        List<String> Nodes = new ArrayList<>();
        List<String> addressIn = new ArrayList<>();
        List<String> addressOut = new ArrayList<>();

        // Loop แต่ละ element ใน nodes
        for (Map<String, String> node : nodes) {
            String nodeId = node.get("id");
            String nodeType = node.get("type");

            // หา addressIn โดยเช็คว่า nodeId ไหนตรงกับ target ของ edges และให้เพิ่มส่วนของ source
            List<String> in = new ArrayList<>();
            for (Map<String, String> edge : edges) {
                if (edge.get("target").equals(nodeId)) {
                    in.add("'" + edge.get("source") + "'");
                }
            }

            // หา addressOut โดยเช็คว่า nodeId ไหนตรงกับ target ของ edges และให้เพิ่มส่วนของ target
            List<String> out = new ArrayList<>();
            for (Map<String, String> edge : edges) {
                if (edge.get("source").equals(nodeId)) {
                    out.add("'" + edge.get("target") + "'");
                }
            }

            // เพิ่มข้อมูลเข้า Lists มี '' ครอบข้อมูลเสมอ
            Nodes.add("'" + nodeType + "'");
            
            // เช็คว่าถ้า addressIn ไม่แสดงข้อมูลให้ใส่ ''
            if (in.isEmpty()) {
                addressIn.add("''");
            } else {
                addressIn.add(String.join(",", in)); // if มีหลายค่าให้เชื่อมด้วย ','
            }

            // เช็คว่าถ้า addressOut ไม่แสดงข้อมูลให้ใส่ ''
            if (out.isEmpty()) {
                addressOut.add("''");
            } else {
                addressOut.add(String.join(",", out)); // if มีหลายค่าให้เชื่อมด้วย ','
            }
        }

        System.out.println("Nodes: " + Nodes);
        System.out.println("addressIn: " + addressIn);
        System.out.println("addressOut: " + addressOut);
    }
}