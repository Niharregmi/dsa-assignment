package gui;
package gui;
package gui;
package gui;
package gui;
package gui;
package gui;import javax.swing.*;
package gui;import java.awt.*;
package gui;import java.util.LinkedList;
package gui;import java.util.PriorityQueue;
package gui;import java.util.Queue;
package gui;import java.util.Random;
package gui;
package gui;public class TrafficGUI extends JFrame {
package gui;    private final Queue<Vehicle> normalQueue = new LinkedList<>();
package gui;    private final PriorityQueue<Vehicle> emergencyQueue = new PriorityQueue<>();
package gui;    private final TrafficLight trafficLight = new TrafficLight();
package gui;
package gui;    private final JTextArea normalQueueArea = new JTextArea();
package gui;    private final JTextArea emergencyQueueArea = new JTextArea();
package gui;    private final JLabel signalLabel = new JLabel("Signal: RED", SwingConstants.CENTER);
package gui;    private final JLabel statusLabel = new JLabel("System running...", SwingConstants.LEFT);
package gui;
package gui;    private final TrafficLightController lightController = new TrafficLightController(trafficLight);
package gui;    private final VehicleProcessor vehicleProcessor = new VehicleProcessor(normalQueue, emergencyQueue, trafficLight);
package gui;    private final EmergencyHandler emergencyHandler = new EmergencyHandler(emergencyQueue, trafficLight, this); // pass this
package gui;
package gui;    private final Random random = new Random();
package gui;    private boolean emergencyModeActive = false;
package gui;
package gui;    public TrafficGUI() {
package gui;        setTitle("🚦 Traffic Signal Management System");
package gui;        setSize(600, 500);
package gui;        setDefaultCloseOperation(EXIT_ON_CLOSE);
package gui;        setLocationRelativeTo(null);
package gui;        setLayout(new BorderLayout(10, 10));
package gui;        setResizable(false);
package gui;
package gui;        // ===== Top panel: Signal Label =====
package gui;        signalLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
package gui;        signalLabel.setOpaque(true);
package gui;        signalLabel.setBackground(Color.RED);
package gui;        signalLabel.setForeground(Color.WHITE);
package gui;        signalLabel.setPreferredSize(new Dimension(getWidth(), 50));
package gui;        add(signalLabel, BorderLayout.NORTH);
package gui;
package gui;        // ===== Center panel: Queues =====
package gui;        JPanel centerPanel = new JPanel();
package gui;        centerPanel.setLayout(new GridLayout(1, 2, 10, 0));
package gui;        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
package gui;
package gui;        // Normal Vehicles panel
package gui;        normalQueueArea.setEditable(false);
package gui;        normalQueueArea.setFont(new Font("Consolas", Font.PLAIN, 14));
package gui;        JScrollPane normalScroll = new JScrollPane(normalQueueArea);
package gui;        JPanel normalPanel = new JPanel(new BorderLayout());
package gui;        normalPanel.setBorder(BorderFactory.createTitledBorder("🚗 Normal Vehicle Queue"));
package gui;        normalPanel.add(normalScroll, BorderLayout.CENTER);
package gui;        centerPanel.add(normalPanel);
package gui;
package gui;        // Emergency Vehicles panel
package gui;        emergencyQueueArea.setEditable(false);
package gui;        emergencyQueueArea.setFont(new Font("Consolas", Font.PLAIN, 14));
package gui;        emergencyQueueArea.setForeground(Color.RED.darker());
package gui;        JScrollPane emergencyScroll = new JScrollPane(emergencyQueueArea);
package gui;        JPanel emergencyPanel = new JPanel(new BorderLayout());
package gui;        emergencyPanel.setBorder(BorderFactory.createTitledBorder("🚑 Emergency Vehicle Queue"));
package gui;        emergencyPanel.add(emergencyScroll, BorderLayout.CENTER);
package gui;        centerPanel.add(emergencyPanel);
package gui;
package gui;        add(centerPanel, BorderLayout.CENTER);
package gui;
package gui;        // ===== Bottom panel: Controls and status =====
package gui;        JPanel bottomPanel = new JPanel();
package gui;        bottomPanel.setLayout(new BorderLayout(10, 10));
package gui;
package gui;        // Buttons Panel
package gui;        JPanel buttonPanel = new JPanel();
package gui;        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
package gui;
package gui;        JButton addVehicleBtn = new JButton("➕ Add Vehicle");
package gui;        JButton toggleSignalBtn = new JButton("🔄 Toggle Signal");
package gui;        JButton addEmergencyBtn = new JButton("🚨 Add Emergency");
package gui;        JButton toggleEmergencyModeBtn = new JButton("⚠️ Enable Emergency Mode");
package gui;
package gui;        addVehicleBtn.setToolTipText("Add a random vehicle (normal or emergency)");
package gui;        toggleSignalBtn.setToolTipText("Manually toggle traffic signal");
package gui;        addEmergencyBtn.setToolTipText("Add emergency vehicle (ambulance)");
package gui;        toggleEmergencyModeBtn.setToolTipText("Enable or disable emergency mode");
package gui;
package gui;        buttonPanel.add(addVehicleBtn);
package gui;        buttonPanel.add(toggleSignalBtn);
package gui;        buttonPanel.add(addEmergencyBtn);
package gui;        buttonPanel.add(toggleEmergencyModeBtn);
package gui;
package gui;        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
package gui;
package gui;        // Status bar
package gui;        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
package gui;        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
package gui;        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
package gui;
package gui;        add(bottomPanel, BorderLayout.SOUTH);
package gui;
package gui;        // ===== Button actions =====
package gui;        addVehicleBtn.addActionListener(e -> {
package gui;            addVehicle();
package gui;            updateStatus("Added a vehicle.");
package gui;        });
package gui;
package gui;        toggleSignalBtn.addActionListener(e -> {
package gui;            if (emergencyModeActive) {
package gui;                updateStatus("Cannot toggle signal while Emergency Mode is active.");
package gui;                return;
package gui;            }
package gui;            synchronized (trafficLight) {
package gui;                trafficLight.nextSignal();  // 3-color cycle
package gui;            }
package gui;            updateSignalLabel();
package gui;            updateStatus("Signal toggled manually.");
package gui;        });
package gui;
package gui;        addEmergencyBtn.addActionListener(e -> {
package gui;            Vehicle emergency = new Vehicle("E" + random.nextInt(1000), Vehicle.Type.AMBULANCE);
package gui;            synchronized (emergencyQueue) {
package gui;                emergencyQueue.add(emergency);
package gui;            }
package gui;            updateStatus("Emergency vehicle added.");
package gui;        });
package gui;
package gui;        toggleEmergencyModeBtn.addActionListener(e -> {
package gui;            emergencyModeActive = !emergencyModeActive;
package gui;            if (emergencyModeActive) {
package gui;                emergencyHandler.enableEmergencyMode();
package gui;                toggleEmergencyModeBtn.setText("⚠️ Disable Emergency Mode");
package gui;                updateStatus("Emergency mode enabled.");
package gui;            } else {
package gui;                emergencyHandler.disableEmergencyMode();
package gui;                toggleEmergencyModeBtn.setText("⚠️ Enable Emergency Mode");
package gui;                updateStatus("Emergency mode disabled.");
package gui;            }
package gui;            updateSignalLabel(); // refresh label (status text)
package gui;        });
package gui;
package gui;        // ===== Start background threads =====
package gui;        lightController.start();
package gui;        vehicleProcessor.start();
package gui;        emergencyHandler.start();
package gui;
package gui;        // ===== Periodic UI update timer =====
package gui;        new Timer(1000, e -> SwingUtilities.invokeLater(() -> {
package gui;            updateDisplay();
package gui;            updateSignalLabel();
package gui;        })).start();
package gui;
package gui;        // Initial UI update
package gui;        updateSignalLabel();
package gui;        updateDisplay();
package gui;
package gui;        setVisible(true);
package gui;    }
package gui;
package gui;    private void addVehicle() {
package gui;        Vehicle v = VehicleGenerator.generateVehicle();
package gui;        if (v.getType() == Vehicle.Type.NORMAL) {
package gui;            synchronized (normalQueue) {
package gui;                normalQueue.add(v);
package gui;            }
package gui;        } else {
package gui;            synchronized (emergencyQueue) {
package gui;                emergencyQueue.add(v);
package gui;            }
package gui;        }
package gui;    }
package gui;
package gui;    public void updateSignalLabel() {
package gui;        TrafficLight.Signal sig = trafficLight.getSignal();
package gui;        signalLabel.setText("Signal: " + sig);
package gui;        switch (sig) {
package gui;            case GREEN -> {
package gui;                signalLabel.setBackground(new Color(0, 153, 0));  // Dark green
package gui;                signalLabel.setForeground(Color.WHITE);
package gui;            }
package gui;            case YELLOW -> {
package gui;                signalLabel.setBackground(new Color(255, 204, 0)); // Bright yellow
package gui;                signalLabel.setForeground(Color.BLACK);
package gui;            }
package gui;            case RED -> {
package gui;                signalLabel.setBackground(new Color(204, 0, 0));  // Dark red
package gui;                signalLabel.setForeground(Color.WHITE);
package gui;            }
package gui;        }
package gui;
package gui;        // Show emergency mode info in status label
package gui;        if (emergencyModeActive) {
package gui;            statusLabel.setText("Status: Emergency Mode ENABLED - Emergency vehicles always pass");
package gui;        } else {
package gui;            statusLabel.setText("Status: Normal mode - Vehicles obey signals");
package gui;        }
package gui;    }
package gui;
package gui;    private void updateDisplay() {
package gui;        StringBuilder normalSb = new StringBuilder();
package gui;        synchronized (normalQueue) {
package gui;            for (Vehicle v : normalQueue) {
package gui;                normalSb.append(v).append("\n");
package gui;            }
package gui;        }
package gui;        normalQueueArea.setText(normalSb.toString());
package gui;
package gui;        StringBuilder emergencySb = new StringBuilder();
package gui;        synchronized (emergencyQueue) {
package gui;            for (Vehicle v : emergencyQueue) {
package gui;                emergencySb.append(v).append("\n");
package gui;            }
package gui;        }
package gui;        emergencyQueueArea.setText(emergencySb.toString());
package gui;    }
package gui;
package gui;    private void updateStatus(String msg) {
package gui;        statusLabel.setText("Status: " + msg);
package gui;    }
package gui;}
