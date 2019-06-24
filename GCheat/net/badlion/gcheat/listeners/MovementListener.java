package net.badlion.gcheat.listeners;

import org.bukkit.*;
import java.util.regex.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import net.badlion.gcheat.gemu.events.*;
import net.badlion.gcheat.*;
import java.util.*;

public class MovementListener implements Listener
{
    private static Map<UUID, List<Long>> lastSpeedHack;
    private static Map<UUID, List<Long>> lastHighSpeedHack;
    private static Map<UUID, List<Long>> lastHoverHack;
    private static Map<UUID, List<Long>> lastSpeedTypeD;
    private static Map<UUID, List<Long>> lastFlyTypeD;
    private static Map<UUID, List<Long>> lastMinijumps;
    private static Map<UUID, Location> teleportMap;
    private static Pattern p;
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        MovementListener.lastSpeedHack.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
        MovementListener.lastHighSpeedHack.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
        MovementListener.lastHoverHack.put(event.getPlayer().getUniqueId(), new ArrayList<Long>());
        MovementListener.teleportMap.put(event.getPlayer().getUniqueId(), null);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        MovementListener.lastSpeedHack.remove(event.getPlayer().getUniqueId());
        MovementListener.lastHighSpeedHack.remove(event.getPlayer().getUniqueId());
        MovementListener.lastHoverHack.remove(event.getPlayer().getUniqueId());
        MovementListener.teleportMap.remove(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent event) {
        MovementListener.teleportMap.put(event.getPlayer().getUniqueId(), event.getTo());
    }
    
    @EventHandler
    public void onPlayerSpeedHacking(final GCheatEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getType:()Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     4: getstatic       net/badlion/gcheat/gemu/events/GCheatEvent$Type.SPEED:Lnet/badlion/gcheat/gemu/events/GCheatEvent$Type;
        //     7: if_acmpne       213
        //    10: getstatic       net/badlion/gcheat/listeners/MovementListener.teleportMap:Ljava/util/Map;
        //    13: aload_1         /* event */
        //    14: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //    17: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //    22: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    27: checkcast       Lorg/bukkit/Location;
        //    30: astore_2        /* tpLocation */
        //    31: aload_2         /* tpLocation */
        //    32: ifnull          56
        //    35: aload_2         /* tpLocation */
        //    36: aload_1         /* event */
        //    37: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //    40: invokeinterface org/bukkit/entity/Player.getLocation:()Lorg/bukkit/Location;
        //    45: invokestatic    net/badlion/gcheat/listeners/MovementListener.getSquareRoot:(Lorg/bukkit/Location;Lorg/bukkit/Location;)D
        //    48: ldc2_w          5.0
        //    51: dcmpg          
        //    52: ifge            56
        //    55: return         
        //    56: aload_1         /* event */
        //    57: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getMsg:()Ljava/lang/String;
        //    60: ldc             "Type C"
        //    62: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    65: ifne            69
        //    68: return         
        //    69: aload_1         /* event */
        //    70: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getMsg:()Ljava/lang/String;
        //    73: astore_3        /* msg */
        //    74: getstatic       net/badlion/gcheat/listeners/MovementListener.p:Ljava/util/regex/Pattern;
        //    77: aload_3         /* msg */
        //    78: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //    81: astore          matcher
        //    83: aload           matcher
        //    85: invokevirtual   java/util/regex/Matcher.find:()Z
        //    88: ifeq            213
        //    91: aload           matcher
        //    93: iconst_1       
        //    94: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //    97: invokestatic    java/lang/Double.parseDouble:(Ljava/lang/String;)D
        //   100: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //   103: astore          lvl
        //   105: goto            141
        //   108: astore          e
        //   110: invokestatic    org/bukkit/Bukkit.getLogger:()invokestatic   !!! ERROR
        //   113: new             Ljava/lang/StringBuilder;
        //   116: dup            
        //   117: invokespecial   java/lang/StringBuilder.<init>:()V
        //   120: ldc             "Wrong Timer int "
        //   122: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   125: aload           matcher
        //   127: iconst_1       
        //   128: invokevirtual   java/util/regex/Matcher.group:(I)Ljava/lang/String;
        //   131: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   134: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   137: invokevirtual   invokevirtual  !!! ERROR
        //   140: return         
        //   141: aload           lvl
        //   143: invokevirtual   java/lang/Double.doubleValue:()D
        //   146: ldc2_w          1.5
        //   149: dcmpl          
        //   150: ifle            184
        //   153: getstatic       net/badlion/gcheat/listeners/MovementListener.lastHighSpeedHack:Ljava/util/Map;
        //   156: aload_1         /* event */
        //   157: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   160: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   165: aload_1         /* event */
        //   166: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   169: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   174: ldc             300000
        //   176: iconst_3       
        //   177: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;II)Z
        //   180: pop            
        //   181: goto            213
        //   184: getstatic       net/badlion/gcheat/listeners/MovementListener.lastSpeedHack:Ljava/util/Map;
        //   187: aload_1         /* event */
        //   188: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   191: invokeinterface org/bukkit/entity/Player.getUniqueId:()Ljava/util/UUID;
        //   196: aload_1         /* event */
        //   197: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //   200: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //   205: ldc             300000
        //   207: bipush          15
        //   209: invokestatic    net/badlion/gcheat/GCheat.handleTimeDetection:(Ljava/util/Map;Ljava/util/UUID;Ljava/lang/String;II)Z
        //   212: pop            
        //   213: return         
        //    StackMapTable: 00 06 FC 00 38 07 00 72 0C FF 00 26 00 05 07 00 73 07 00 74 07 00 72 07 00 75 07 00 76 00 01 07 00 77 FC 00 20 07 00 78 2A FF 00 1C 00 02 07 00 73 07 00 74 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  91     105    108    141    Ljava/lang/NumberFormatException;
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
    
    @EventHandler
    public void onLastHoveHacking(final GCheatEvent event) {
        if (event.getType() == GCheatEvent.Type.HOVER) {
            final Location tpLocation = MovementListener.teleportMap.get(event.getPlayer().getUniqueId());
            if (tpLocation != null && getSquareRoot(tpLocation, event.getPlayer().getLocation()) < 5.0) {
                return;
            }
            GCheat.handleTimeDetection(MovementListener.lastHoverHack, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 300000, 5);
        }
    }
    
    @EventHandler
    public void onSpeedFlyTypeD(final GCheatEvent event) {
        if (event.getType() == GCheatEvent.Type.SPEED) {
            if (event.getMsg().contains("Type D")) {
                GCheat.handleTimeDetection(MovementListener.lastSpeedTypeD, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 10000, 8);
            }
        }
        else if (event.getType() == GCheatEvent.Type.FLY && event.getMsg().contains("Type D")) {
            GCheat.handleTimeDetection(MovementListener.lastFlyTypeD, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 10000, 8);
        }
    }
    
    @EventHandler
    public void onMinijumps(final GCheatEvent event) {
        if (event.getType() == GCheatEvent.Type.CRIT && event.getMsg().contains("uses minijumps")) {
            GCheat.handleTimeDetection(MovementListener.lastMinijumps, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 30000, 10);
        }
    }
    
    public static double getSquareRoot(final Location location, final Location location2) {
        return Math.sqrt(Math.pow(location.getX() - location2.getX(), 2.0) + Math.pow(location.getZ() - location2.getZ(), 2.0));
    }
    
    static {
        MovementListener.lastSpeedHack = new HashMap<UUID, List<Long>>();
        MovementListener.lastHighSpeedHack = new HashMap<UUID, List<Long>>();
        MovementListener.lastHoverHack = new HashMap<UUID, List<Long>>();
        MovementListener.lastSpeedTypeD = new HashMap<UUID, List<Long>>();
        MovementListener.lastFlyTypeD = new HashMap<UUID, List<Long>>();
        MovementListener.lastMinijumps = new HashMap<UUID, List<Long>>();
        MovementListener.teleportMap = new HashMap<UUID, Location>();
        MovementListener.p = Pattern.compile("VL ([0-9]*\\.[0-9]+|[0-9]+)");
    }
}
