import java.util.ArrayList;
import java.util.List;

class Edge {
    String id;
    String source;
    String target;

    public Edge(String id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }
}

class Node {
    String id;
    String type;
    String page;

    public Node(String id, String type, String page) {
        this.id = id;
        this.type = type;
        this.page = page;
    }
}

class NodeInfo {
    String nodeType;
    String addressIn;
    String addressOut;

    public NodeInfo(String nodeType, String addressIn, String addressOut) {
        this.nodeType = nodeType;
        this.addressIn = addressIn;
        this.addressOut = addressOut;
    }

    @Override
    public String toString() {
        return "Nodes = ['" + nodeType + "'], " +
               "addressIn = ['" + addressIn + "'], " +
               "addressOut = ['" + addressOut + "']";
    }
}

public class Java {
    // function สำหรับสร้าง Nodes, addressIn, addressOut
    public static List<NodeInfo> createNodeInfoList(List<Node> nodes, List<Edge> edges) {
        List<NodeInfo> nodeInfos = new ArrayList<>();

        for (Node node : nodes) {
            String addressIn = "";
            String addressOut = "";

            // หา addressIn จาก Node ที่ชี้ไป
            for (Edge edge : edges) {
                if (edge.target.equals(node.id)) {
                    addressIn = edge.source;
                    break;
                }
            }

            // หา addressOut จาก Node ที่ชี้ไป
            for (Edge edge : edges) {
                if (edge.source.equals(node.id)) {
                    if (!addressOut.isEmpty()) {
                        addressOut += ", ";
                    }
                    addressOut += edge.target;
                }
            }

            // เช็คตามเงื่อนไขที่ว่า
            // Input : มีเฉพาะ addressOut
            // OpenAI Embedding : มี addressIn และ addressOut
            // To publish : มี addressIn และ addressOut
            // Line, Facebook, Discord, Output : มีเฉพาะ addressIn
            switch (node.type) {
                case "input":
                    addressIn = "";
                    break;
                case "org.maoz.prehandle.workers.neoai.notify.LineVerticle":
                case "org.maoz.prehandle.workers.neoai.notify.FacebookVerticle":
                case "org.maoz.prehandle.workers.neoai.notify.DiscordVerticle":
                case "org.maoz.prehandle.workers.neoai.output.OutputVerticle":
                    addressOut = "";
                    break;
            }

            nodeInfos.add(new NodeInfo(node.type, addressIn, addressOut));
        }

        return nodeInfos;
    }

    // สร้างรูปแบบ String ตามผลลัพธ์ที่ต้องการของ Nodes
    public static String getNodeTypes(List<NodeInfo> nodeInfos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nodeInfos.size(); i++) {
            sb.append("'").append(nodeInfos.get(i).nodeType).append("'");
            if (i < nodeInfos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // สร้างรูปแบบ String ตามผลลัพธ์ที่ต้องการของ addressIn
    public static String getAddressIn(List<NodeInfo> nodeInfos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nodeInfos.size(); i++) {
            sb.append("'").append(nodeInfos.get(i).addressIn).append("'");
            if (i < nodeInfos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // สร้างรูปแบบ String ตามผลลัพธ์ที่ต้องการของ addressOut
    public static String getAddressOut(List<NodeInfo> nodeInfos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nodeInfos.size(); i++) {
            sb.append("'").append(nodeInfos.get(i).addressOut).append("'");
            if (i < nodeInfos.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge("xy-edge__input-node-1-voyage-embed-node-2", "input-node-1", "voyage-embed-node-2"));
        edges.add(new Edge("xy-edge__voyage-embed-node-2-http-client-adapter-verticle-node-4", "voyage-embed-node-2", "http-client-adapter-verticle-node-4"));
        edges.add(new Edge("xy-edge__http-client-adapter-verticle-node-4-voyage-transform-node-3", "http-client-adapter-verticle-node-4", "voyage-transform-node-3"));
        edges.add(new Edge("xy-edge__voyage-transform-node-3-to-publish-verticle-node-10", "voyage-transform-node-3", "to-publish-verticle-node-10"));
        edges.add(new Edge("xy-edge__to-publish-verticle-node-10-line-node-7", "to-publish-verticle-node-10", "line-node-7"));
        edges.add(new Edge("xy-edge__to-publish-verticle-node-10-facebook-node-8", "to-publish-verticle-node-10", "facebook-node-8"));
        edges.add(new Edge("xy-edge__to-publish-verticle-node-10-discord-node-9", "to-publish-verticle-node-10", "discord-node-9"));
        edges.add(new Edge("xy-edge__to-publish-verticle-node-10-output-node-10", "to-publish-verticle-node-10", "output-node-10"));

        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("input-node-1", "input", "embedding"));
        nodes.add(new Node("voyage-embed-node-2", "org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle", "embedding"));
        nodes.add(new Node("http-client-adapter-verticle-node-4", "org.maoz.prehandle.workers.neoai.httpclient.HttpClientAdapterVerticle", "embedding"));
        nodes.add(new Node("voyage-transform-node-3", "org.maoz.prehandle.workers.neoai.aiclient.embedding.util.VoyageTransformVerticle", "embedding"));
        nodes.add(new Node("to-publish-verticle-node-10", "org.maoz.prehandle.workers.neoai.ebtransform.ToPublishVerticle", "embedding"));
        nodes.add(new Node("line-node-7", "org.maoz.prehandle.workers.neoai.notify.LineVerticle", "embedding"));
        nodes.add(new Node("facebook-node-8", "org.maoz.prehandle.workers.neoai.notify.FacebookVerticle", "embedding"));
        nodes.add(new Node("discord-node-9", "org.maoz.prehandle.workers.neoai.notify.DiscordVerticle", "embedding"));
        nodes.add(new Node("output-node-10", "org.maoz.prehandle.workers.neoai.output.OutputVerticle", "embedding"));

        List<NodeInfo> nodeInfos = createNodeInfoList(nodes, edges);

        System.out.println("Nodes = " + getNodeTypes(nodeInfos));
        System.out.println("addressIn = " + getAddressIn(nodeInfos));
        System.out.println("addressOut = " + getAddressOut(nodeInfos));
    }
}