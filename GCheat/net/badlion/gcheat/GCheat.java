package net.badlion.gcheat;

import org.bukkit.plugin.java.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.messaging.*;
import net.badlion.gcheat.listeners.*;
import org.bukkit.scheduler.*;
import net.badlion.gcheat.gemu.events.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;
import org.joda.time.*;

public class GCheat extends JavaPlugin implements Listener
{
    public static GCheat plugin;
    public static final Random random;
    private Logger chatLogger;
    private Marker marker;
    private boolean logMessages;
    private String serverName;
    private Queue<GCheatRecord> records;
    private Queue<SwingListener.SwingTracker> swingRecords;
    public static Set<String> disabledPlayers;
    public static Set<String> ignoredPlayers;
    public static Set<UUID> bannedUUIDS;
    private static Map<UUID, List<Long>> killAuraViolationsTypeC;
    private static Map<UUID, Long> ignoreHI;
    private static Map<UUID, List<Long>> killAuraViolationsTypeHShort;
    private static Map<UUID, List<Long>> killAuraViolationsTypeHLong;
    private static Map<UUID, List<Long>> killAuraViolationsTypeIShort;
    private static Map<UUID, List<Long>> killAuraViolationsTypeILong;
    private static Map<UUID, List<Long>> killAuraViolationsTypeS;
    private static Map<UUID, List<Long>> killAuraViolationsTypeT8;
    private static Map<UUID, List<Long>> killAuraViolationsTypeT9;
    private static Map<UUID, List<Long>> criticalsTypeB;
    private static Map<UUID, List<Long>> badPacketsTypeA;
    private static Map<UUID, List<Long>> Pluginlistener;
    
    public GCheat() {
        this.logMessages = false;
        this.records = new ConcurrentLinkedQueue<GCheatRecord>();
        this.swingRecords = new ConcurrentLinkedQueue<SwingListener.SwingTracker>();
    }
    
    public void onEnable() {
        (GCheat.plugin = this).saveDefaultConfig();
        this.chatLogger = LogManager.getLogger(GCheat.class.getName());
        this.marker = MarkerManager.getMarker("GCHEAT");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new AutoClickerListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new GSyncListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryTweakListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new FoodFixListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new MovementListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new RegenListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SwingListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockGlitchListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ReducedKnockbackListener(), (Plugin)this);
        final BungeeCordListener bungeeCordListener = new BungeeCordListener();
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", (PluginMessageListener)bungeeCordListener);
        this.getServer().getPluginManager().registerEvents((Listener)bungeeCordListener, (Plugin)this);
        final VapeListener vapeListener = new VapeListener();
        this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "LOLIMAHCKER", (PluginMessageListener)vapeListener);
        this.getServer().getPluginManager().registerEvents((Listener)vapeListener, (Plugin)this);
        new BukkitRunnable() {
            public void run() {
                GCheat.this.insertGCheatRecords();
                GCheat.this.insertGCheatSwingRecords();
            }
        }.runTaskTimerAsynchronously((Plugin)this, 100L, 100L);
        this.getCommand("dgcheat").setExecutor((CommandExecutor)new DisableGCheatCommand());
    }
    
    public void onDisable() {
        this.insertGCheatRecords();
    }
    
    @EventHandler(ignoreCancelled = true)
    public void gCheatLogEventNew(final GCheatEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_1         /* event */
        //     4: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //     7: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //    12: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    15: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //    20: ifne            55
        //    23: getstatic       net/badlion/gcheat/GCheat.ignoredPlayers:Ljava/util/Set;
        //    26: aload_1         /* event */
        //    27: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //    30: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //    35: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    38: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //    43: ifeq            47
        //    46: return         
        //    47: aload_0         /* this */
        //    48: aload_1         /* event */
        //    49: invokevirtual   net/badlion/gcheat/GCheat.handleAutomaticBans:(Lnet/badlion/gcheat/gemu/events/GCheatEvent;)V
        //    52: goto            65
        //    55: invokestatic    org/bukkit/Bukkit.getLogger:()invokestatic   !!! ERROR
        //    58: aload_1         /* event */
        //    59: invokevirtual   net/badlion/gcheat/gemu/events/GCheatEvent.getMsg:()Ljava/lang/String;
        //    62: invokevirtual   invokevirtual  !!! ERROR
        //    65: return         
        //    StackMapTable: 00 03 2F 07 09
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
    
    public void handleAutomaticBans(final GCheatEvent event) {
        if (event.getType() == GCheatEvent.Type.KILL_AURA) {
            if (event.getMsg().contains("Type C")) {
                final int lvl = Integer.parseInt(event.getMsg().substring(event.getMsg().length() - 1));
                if (lvl <= 7) {
                    List<Long> times = GCheat.killAuraViolationsTypeC.get(event.getPlayer().getUniqueId());
                    if (times == null) {
                        times = new ArrayList<Long>();
                        GCheat.killAuraViolationsTypeC.put(event.getPlayer().getUniqueId(), times);
                    }
                    int violations = 0;
                    final Long currentTime = System.currentTimeMillis();
                    times.add(currentTime);
                    final Iterator<Long> iterator = times.iterator();
                    while (iterator.hasNext()) {
                        final Long time = iterator.next();
                        if (time + 300000L >= currentTime) {
                            ++violations;
                        }
                        else {
                            iterator.remove();
                        }
                    }
                    if (((lvl == 1 || (lvl >= 4 && lvl <= 7)) && violations == 5) || ((lvl == 2 || lvl == 3) && violations == 10)) {
                        if (event.getPlayer().getName().equals("GCheat")) {
                            return;
                        }
                        if (!GCheat.bannedUUIDS.contains(event.getPlayer().getUniqueId())) {
                            GCheat.bannedUUIDS.add(event.getPlayer().getUniqueId());
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + event.getPlayer().getName() + " [GCheat] Unfair Advantage");
                        }
                    }
                }
            }
            else if (event.getMsg().contains("Type E2")) {
                delayedBan(event.getPlayer());
            }
            else if (event.getMsg().contains("Type H")) {
                if (event.getMsg().contains("Type H (Experimental) 1") || event.getMsg().contains("Type H (Experimental) 2")) {
                    final Long ts = GCheat.ignoreHI.get(event.getPlayer().getUniqueId());
                    if ((ts == null || ts + 5000L < System.currentTimeMillis()) && !handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeHShort, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 10, event.getMsg())) {
                        handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeHLong, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 3600000, 30, event.getMsg());
                    }
                }
                else if (event.getMsg().contains("Type H | ")) {
                    String[] parts = event.getMsg().split(" \\| ");
                    final String[] split;
                    parts = (split = parts[1].split(" "));
                    for (final String part : split) {
                        final int val = Integer.parseInt(part.replace(" ", ""));
                        if (val > 2) {
                            GCheat.killAuraViolationsTypeHShort.remove(event.getPlayer().getUniqueId());
                            GCheat.killAuraViolationsTypeHLong.remove(event.getPlayer().getUniqueId());
                            GCheat.ignoreHI.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                        }
                        else {
                            final Long ts2 = GCheat.ignoreHI.get(event.getPlayer().getUniqueId());
                            if ((ts2 == null || ts2 + 5000L < System.currentTimeMillis()) && !handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeHShort, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 10, event.getMsg())) {
                                handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeHLong, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 3600000, 30, event.getMsg());
                            }
                        }
                    }
                }
                else {
                    GCheat.killAuraViolationsTypeHShort.remove(event.getPlayer().getUniqueId());
                    GCheat.killAuraViolationsTypeHLong.remove(event.getPlayer().getUniqueId());
                    GCheat.ignoreHI.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                }
            }
            else if (event.getMsg().contains("Type I")) {
                if (event.getMsg().contains("Type I (Experimental) 1") || event.getMsg().contains("Type I (Experimental) 2")) {
                    final Long ts = GCheat.ignoreHI.get(event.getPlayer().getUniqueId());
                    if ((ts == null || ts + 5000L < System.currentTimeMillis()) && !handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeIShort, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 10, event.getMsg())) {
                        handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeILong, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 3600000, 30, event.getMsg());
                    }
                }
                else if (event.getMsg().contains("Type I | ")) {
                    String[] parts = event.getMsg().split(" \\| ");
                    final String[] split2;
                    parts = (split2 = parts[1].split(" "));
                    for (final String part : split2) {
                        final int val = Integer.parseInt(part.replace(" ", ""));
                        if (val > 2) {
                            GCheat.killAuraViolationsTypeIShort.remove(event.getPlayer().getUniqueId());
                            GCheat.killAuraViolationsTypeILong.remove(event.getPlayer().getUniqueId());
                            GCheat.ignoreHI.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                        }
                        else {
                            final Long ts2 = GCheat.ignoreHI.get(event.getPlayer().getUniqueId());
                            if ((ts2 == null || ts2 + 5000L < System.currentTimeMillis()) && !handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeIShort, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 10, event.getMsg())) {
                                handleTimeDetectionWithSkips(GCheat.killAuraViolationsTypeILong, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 3600000, 30, event.getMsg());
                            }
                        }
                    }
                }
                else {
                    GCheat.killAuraViolationsTypeIShort.remove(event.getPlayer().getUniqueId());
                    GCheat.killAuraViolationsTypeILong.remove(event.getPlayer().getUniqueId());
                    GCheat.ignoreHI.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                }
            }
            else if (event.getMsg().contains("Type J") || event.getMsg().contains("Type K")) {
                final long currentTime2 = System.currentTimeMillis() / 1000L;
                removeLogs(GCheat.killAuraViolationsTypeHShort, event.getPlayer().getUniqueId(), currentTime2);
                removeLogs(GCheat.killAuraViolationsTypeHLong, event.getPlayer().getUniqueId(), currentTime2);
                removeLogs(GCheat.killAuraViolationsTypeIShort, event.getPlayer().getUniqueId(), currentTime2);
                removeLogs(GCheat.killAuraViolationsTypeILong, event.getPlayer().getUniqueId(), currentTime2);
            }
            else if (event.getMsg().contains("Type U")) {
                handleTimeDetection(GCheat.killAuraViolationsTypeS, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 60000, 3);
            }
            else if (event.getMsg().contains("Type T (8)")) {
                handleTimeDetection(GCheat.killAuraViolationsTypeT8, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 10);
            }
            else if (event.getMsg().contains("Type T (9)")) {
                handleTimeDetection(GCheat.killAuraViolationsTypeT9, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 600000, 5);
            }
        }
        else if (event.getType() == GCheatEvent.Type.REGEN) {
            if (event.getMsg().contains("Regen Type B")) {
                delayedBan(event.getPlayer());
            }
        }
        else if (event.getType() == GCheatEvent.Type.CRIT) {
            if (event.getMsg().contains("Criticals Type B")) {
                handleTimeDetection(GCheat.criticalsTypeB, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 60000, 10);
            }
        }
        else if (event.getType() == GCheatEvent.Type.UNKNOWN && event.getMsg().contains("Bad Packets Type A") && (event.getMsg().endsWith("(PacketPlayInArmAnimation)") || event.getMsg().endsWith("(PacketPlayInEntityAction)") || event.getMsg().endsWith("(PacketPlayInUseEntity)"))) {
            handleTimeDetection(GCheat.badPacketsTypeA, event.getPlayer().getUniqueId(), event.getPlayer().getName(), 60000, 2);
        }
    }
    
    public void insertGCheatRecords() {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
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
        //     at com.strobel.assembler.metadata.MetadataHelper.isRawType(MetadataHelper.java:1581)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visitClassType(MetadataHelper.java:2361)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visitClassType(MetadataHelper.java:2322)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.accept(CoreMetadataFactory.java:577)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visit(MetadataHelper.java:2336)
        //     at com.strobel.assembler.metadata.MetadataHelper.isSameType(MetadataHelper.java:1411)
        //     at com.strobel.assembler.metadata.TypeReference.equals(TypeReference.java:118)
        //     at com.strobel.core.Comparer.equals(Comparer.java:31)
        //     at com.strobel.assembler.ir.FrameValue.equals(FrameValue.java:72)
        //     at com.strobel.core.Comparer.equals(Comparer.java:31)
        //     at com.strobel.assembler.ir.Frame.merge(Frame.java:338)
        //     at com.strobel.assembler.ir.Frame.merge(Frame.java:273)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2206)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
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
    
    public void insertGCheatSwingRecords() {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
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
        //     at com.strobel.assembler.metadata.MetadataHelper.isRawType(MetadataHelper.java:1581)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visitClassType(MetadataHelper.java:2361)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visitClassType(MetadataHelper.java:2322)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.accept(CoreMetadataFactory.java:577)
        //     at com.strobel.assembler.metadata.MetadataHelper$SameTypeVisitor.visit(MetadataHelper.java:2336)
        //     at com.strobel.assembler.metadata.MetadataHelper.isSameType(MetadataHelper.java:1411)
        //     at com.strobel.assembler.metadata.TypeReference.equals(TypeReference.java:118)
        //     at com.strobel.core.Comparer.equals(Comparer.java:31)
        //     at com.strobel.assembler.ir.FrameValue.equals(FrameValue.java:72)
        //     at com.strobel.core.Comparer.equals(Comparer.java:31)
        //     at com.strobel.assembler.ir.Frame.merge(Frame.java:338)
        //     at com.strobel.assembler.ir.Frame.merge(Frame.java:273)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2206)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
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
    
    public static boolean handleTimeDetection(final Map<UUID, List<Long>> map, final UUID uuid, final String name, final int length, final int violationCount) {
        return handleTimeDetection(map, uuid, name, length, violationCount, " [GCheat] Unfair Advantage");
    }
    
    public static boolean handleTimeDetection(final Map<UUID, List<Long>> map, final UUID uuid, final String name, final int length, final int violationCount, final String banReason) {
        List<Long> times = map.get(uuid);
        if (times == null) {
            times = new ArrayList<Long>();
            times.add(System.currentTimeMillis());
            map.put(uuid, times);
        }
        else {
            final long currentTime = System.currentTimeMillis();
            int violations = 0;
            times.add(currentTime);
            final Iterator<Long> iterator = times.iterator();
            while (iterator.hasNext()) {
                final Long time = iterator.next();
                if (time + length >= currentTime) {
                    ++violations;
                }
                else {
                    iterator.remove();
                }
            }
            if (violations >= violationCount) {
                if (!GCheat.disabledPlayers.contains(name.toLowerCase()) && !GCheat.bannedUUIDS.contains(uuid)) {
                    GCheat.bannedUUIDS.add(uuid);
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + name + banReason);
                }
                return true;
            }
        }
        return false;
    }
    
    public static void removeLogs(final Map<UUID, List<Long>> map, final UUID uuid, final long time) {
        final List<Long> times = map.get(uuid);
        if (times != null) {
            times.remove(time);
        }
    }
    
    public static boolean handleTimeDetectionWithSkips(final Map<UUID, List<Long>> map, final UUID uuid, final String name, final int length, final int violationCount, final String msg) {
        List<Long> times = map.get(uuid);
        if (times == null) {
            times = new ArrayList<Long>();
            times.add(System.currentTimeMillis() / 1000L);
            map.put(uuid, times);
        }
        else {
            final long currentTime = System.currentTimeMillis() / 1000L;
            if (times.contains(currentTime)) {
                return false;
            }
            int violations = 0;
            times.add(currentTime);
            final Iterator<Long> iterator = times.iterator();
            while (iterator.hasNext()) {
                final Long time = iterator.next();
                if (time + length / 1000 >= currentTime) {
                    ++violations;
                }
                else {
                    iterator.remove();
                }
            }
            if (violations >= violationCount) {
                if (!GCheat.disabledPlayers.contains(name.toLowerCase()) && !GCheat.bannedUUIDS.contains(uuid)) {
                    GCheat.bannedUUIDS.add(uuid);
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + name + " [GCheat] Unfair Advantage");
                }
                return true;
            }
        }
        return false;
    }
    
    public void addSwingRecord(final SwingListener.SwingTracker swingTracker) {
        this.swingRecords.add(swingTracker);
    }
    
    public static void delayedBan(final Player player) {
        if (GCheat.bannedUUIDS.add(player.getUniqueId())) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + player.getName() + " [GCheat] Unfair Advantage");
        }
    }
    
    static {
        random = new Random();
        GCheat.disabledPlayers = new HashSet<String>();
        GCheat.ignoredPlayers = new HashSet<String>();
        GCheat.bannedUUIDS = new HashSet<UUID>();
        GCheat.killAuraViolationsTypeC = new HashMap<UUID, List<Long>>();
        GCheat.ignoreHI = new HashMap<UUID, Long>();
        GCheat.killAuraViolationsTypeHShort = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeHLong = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeIShort = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeILong = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeS = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeT8 = new HashMap<UUID, List<Long>>();
        GCheat.killAuraViolationsTypeT9 = new HashMap<UUID, List<Long>>();
        GCheat.criticalsTypeB = new HashMap<UUID, List<Long>>();
        GCheat.badPacketsTypeA = new HashMap<UUID, List<Long>>();
        GCheat.Pluginlistener = new HashMap<UUID, List<Long>>();
    }
    
    public class GCheatRecord
    {
        private Player player;
        private String msg;
        private long ts;
        
        public GCheatRecord(final Player player, final String msg) {
            this.player = player;
            this.msg = msg;
            this.ts = new DateTime(DateTimeZone.UTC).getMillis();
        }
        
        public Player getPlayer() {
            return this.player;
        }
        
        public String getMsg() {
            return this.msg;
        }
        
        public long getTs() {
            return this.ts;
        }
    }
}
