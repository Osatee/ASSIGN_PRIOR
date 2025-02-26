# คำอธิบายของงานนี้
โจทย์ต้องการแปลง rawdata ที่ให้มาให้แสดงในรูปแบบ array index based โดยมีข้อกำหนดของแต่ละ Nodes คือ
1. Input : มีเฉพาะ addressOut
2. OpenAI Embedding : มีทั้ง addressIn และ addressOut
3. To publish : มีทั้ง addressIn และ addressOut
4. Line, Facebook, Discord และ Output : มีเฉพาะ addressIn

ซึ่งจาก rawdata ที่ให้มาจะมี List อยู่ 2 ส่วน คือ nodes และ edges
1. List ของ nodes จะเป็น List ที่บ่งบอกว่า Node นั้นคือ Node อะไรโดยมี key 'type' กำหนดชื่อของ Node เช่น Node ของ Input 'type' ของมันก็คือ 'input'

2. List ของ edges เป็น List ที่แสดงถึงการทำงานของแต่ละ Node ว่า Node นี้มีการไหลของข้อมูลอย่างไรตามภาพในเอกสารซึ่ง edges จะเป็น n-1 ของ nodes เสมอ
       ![image](https://github.com/user-attachments/assets/d938ae5f-24ae-4626-ba8b-c1ab3fb2b216)

เช่น edges นี้คือการไหลของข้อมูลที่มีความสัมพันธ์ระหว่างโหนด 'input' กับ 'org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle'

            {
			"id": "xy-edge__input-node-1-voyage-embed-node-2",
			"source": "input-node-1",
			"target": "voyage-embed-node-2"
		}
  
addressIn : คือ "input-node-1" (ขาเข้า)

addressOut : คือ "voyage-embed-node-2" (ขาออก)

และ 'id' ก็เป็นเหมือนชื่อที่บ่งบอกว่าคือการทำงานระหว่างโหนดต้นทางกับโหนดปลายทางตัวใด

ซึ่งหากทำครบตามที่โจทย์ต้องการผลลัพธ์ที่คาดว่าจะเป็นจะออกมาดังนี้

Nodes = ['input', 'org.maoz.prehandle.workers.neoai.aiclient.embedding.VoyageVerticle', 'org.maoz.prehandle.workers.neoai.httpclient.HttpClientAdapterVerticle', 'org.maoz.prehandle.workers.neoai.aiclient.embedding.util.VoyageTransformVerticle', 'org.maoz.prehandle.workers.neoai.ebtransform.ToPublishVerticle', 'org.maoz.prehandle.workers.neoai.notify.LineVerticle', 'org.maoz.prehandle.workers.neoai.notify.FacebookVerticle', 'org.maoz.prehandle.workers.neoai.notify.DiscordVerticle', 'org.maoz.prehandle.workers.neoai.output.OutputVerticle']

addressIn = ['', 'input-node-1', 'voyage-embed-node-2', 'http-client-adapter-verticle-node-4', 'voyage-transform-node-3', 'to-publish-verticle-node-10', 'to-publish-verticle-node-10', 'to-publish-verticle-node-10', 'to-publish-verticle-node-10']

addressOut = ['voyage-embed-node-2', 'http-client-adapter-verticle-node-4', 'voyage-transform-node-3', 'to-publish-verticle-node-10', 'line-node-7, facebook-node-8, discord-node-9, output-node-10', '', '', '', '']

โดยลำดับตาม List ของ nodes ที่ rawdata ให้มา
