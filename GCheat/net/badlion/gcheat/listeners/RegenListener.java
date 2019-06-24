package net.badlion.gcheat.listeners;

import java.util.regex.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import net.badlion.gcheat.gemu.events.*;
import java.util.*;

public class RegenListener implements Listener
{
    private static Map<UUID, List<Long>> lastTimerHacks;
    private static Map<UUID, List<Long>> lastHighTimerHacks;
    private static Deque<FalseRecord> lastFalseTimerHackers;
    private static Pattern p;
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        RegenListener.lastTimerHacks.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        RegenListener.lastTimerHacks.remove(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerMassPacketHack(final GCheatEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getType:()Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     4: getstatic       net/badlion/gcheat/gemu/events/GCheatEvent$Type.TIMER:Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     7: if_acmpne       285
        //    10: aload_1         /* event */
        //    11: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getMsg:()Ljava/lang/String;
        //    14: astore_2        /* msg */
        //    15: getstatic       net/badlion/gcheat/listeners/RegenListener.p:Ljava/util/regex/Pattern;
        //    18: aload_2         /* msg */
        //    19: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //    22: astore_3        /* matcher */
        //    23: aload_3         /* matcher */
        //    24: invokevirtual   java/util/regex/Matcher.find:()Z
        //    27: ifeq            285
        //    30: aload_3         /* matcher */
        //    31: iconst_1       
        //    32: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //    35: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    38: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    41: astore          lvl
        //    43: goto            78
        //    46: astore          e
        //    48: invokestatic    org/bukkit/Bukkit.getLogger:()invokestatic   !!! ERROR
        //    51: new             Ljava/lang/StringBuilder;
        //    54: dup            
        //    55: invokespecial   java/lang/StringBuilder.<init>:()V
        //    58: ldc             "Wrong Timer int "
        //    60: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    63: aload_3         /* matcher */
        //    64: iconst_1       
        //    65: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //    68: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    71: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    74: invokevirtual   invokevirtual  !!! ERROR
        //    77: return         
        //    78: new             Lnet/badlion/gcheat/listeners/RegenListener$FalseRecord;
        //    81: dup            
        //    82: aload_0         /* this */
        //    83: aload_1         /* event */
        //    84: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //    87: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //    92: invokestatic    java/lang/System.currentTimeMillis:()J
        //    95: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //    98: invokespecial   net/badlion/gcheat/listeners/RegenListener$FalseRecord.<init>:(Lnet/badlion/gcheat/listeners/RegenListener;Ljava/util/UUID;Ljava/lang/Long;)V
        //   101: astore          record
        //   103: new             Ljava/util/HashSet;
        //   106: dup            
        //   107: invokespecial   java/util/HashSet.<init>:()V
        //   110: astore          lastUUIDS
        //   112: getstatic       net/badlion/gcheat/listeners/RegenListener.lastFalseTimerHackers:Ljava/util/Deque;
        //   115: invokeinterface java/util/Deque.iterator:()Ljava/util/Iterator;
        //   120: astore          7
        //   122: aload           7
        //   124: invokeinterface java/util/Iterator.hasNext:()Z
        //   129: ifeq            160
        //   132: aload           7
        //   134: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   139: checkcast       Lnet/badlion/gcheat/listeners/RegenListener$FalseRecord;
        //   142: astore          r
        //   144: aload           lastUUIDS
        //   146: aload           r
        //   148: invokevirtual   net/badlion/gcheat/listeners/RegenListener$FalseRecord.getUuid:()Ljava/util/UUID;
        //   151: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   156: pop            
        //   157: goto            122
        //   160: aload           lastUUIDS
        //   162: invokeinterface java/util/Set.size:()I
        //   167: iconst_5       
        //   168: if_icmple       180
        //   171: getstatic       net/badlion/gcheat/listeners/RegenListener.lastFalseTimerHackers:Ljava/util/Deque;
        //   174: invokeinterface java/util/Deque.clear:()V
        //   179: return         
        //   180: getstatic       net/badlion/gcheat/listeners/RegenListener.lastFalseTimerHackers:Ljava/util/Deque;
        //   183: invokeinterface java/util/Deque.size:()I
        //   188: bipush          10
        //   190: if_icmpne       202
        //   193: getstatic       net/badlion/gcheat/listeners/RegenListener.lastFalseTimerHackers:Ljava/util/Deque;
        //   196: invokeinterface java/util/Deque.removeFirst:()Ljava/lang/Object;
        //   201: pop            
        //   202: getstatic       net/badlion/gcheat/listeners/RegenListener.lastFalseTimerHackers:Ljava/util/Deque;
        //   205: aload           record
        //   207: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //   212: pop            
        //   213: aload           lvl
        //   215: invokevirtual   java/lang/Integer.intValue:()I
        //   218: sipush          250
        //   221: if_icmple       256
        //   224: getstatic       net/badlion/gcheat/listeners/RegenListener.lastHighTimerHacks:Ljava/util/Map;
        //   227: aload_1         /* event */
        //   228: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   231: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   236: aload_1         /* event */
        //   237: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   240: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   245: sipush          30000
        //   248: iconst_3       
        //   249: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;II)Z
        //   252: pop            
        //   253: goto            285
        //   256: getstatic       net/badlion/gcheat/listeners/RegenListener.lastTimerHacks:Ljava/util/Map;
        //   259: aload_1         /* event */
        //   260: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   263: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   268: aload_1         /* event */
        //   269: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   272: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   277: ldc             300000
        //   279: bipush          15
        //   281: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;II)Z
        //   284: pop            
        //   285: return         
        //    StackMapTable: 00 08 FF 00 2E 00 04 07 00 6A 07 00 6B 07 00 6C 07 00 6D 00 01 07 00 6E FC 00 1F 07 00 6F FE 00 2B 07 00 70 07 00 71 07 00 72 FA 00 25 13 15 35 FF 00 1C 00 02 07 00 6A 07 00 6B 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  30     43     46     78     Ljava/lang/NumberFormatException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Invalid BootstrapMethods attribute entry: 2 additional arguments required for method java/lang/invoke/StringConcatFactory.makeConcatWithConstants, but only 1 specified.
        //     at com.strobel.assembler.ir.Error.invalidBootstrapMethodEntry(Error.java:244)
        //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:267)
        //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
        //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:426)
        //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
        //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.base/java.lang.Thread.run(Thread.java:835)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        RegenListener.lastTimerHacks = new HashMap<UUID, List<Long>>();
        RegenListener.lastHighTimerHacks = new HashMap<UUID, List<Long>>();
        RegenListener.lastFalseTimerHackers = new LinkedList<FalseRecord>();
        RegenListener.p = Pattern.compile("mass packet hacking Type B VL(\\d+)");
    }
    
    public class FalseRecord
    {
        private UUID uuid;
        private Long ts;
        
        public FalseRecord(final UUID uuid, final Long ts) {
            this.uuid = uuid;
            this.ts = ts;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        public Long getTs() {
            return this.ts;
        }
    }
}
