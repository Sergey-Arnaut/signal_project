Overview
The Alert Generation subsystem continuously evaluates each patient’s vital measurements against personalized thresholds and, upon violation, creates and routes an Alert to on-call staff.

Class Responsibilities

Alert encapsulates the patient identifier, the triggered condition, and the timestamp.
ThresholdRule represents a single “type > limit” rule (e.g., heart rate > 130 bpm). This allows multiple, reusable rules per patient.
DataStorage abstracts retrieval of the latest measurements (e.g., via database or in-memory cache).
AlertGenerator holds per-patient ThresholdRule sets and orchestrates evaluation: fetching data, checking rules, and delegating to AlertManager.
AlertManager decouples creation from delivery: it looks up the responsible MedicalStaff via StaffDirectory and handles dispatch (e.g., SMS, email).
StaffDirectory models mapping from patient → staff, supporting 1..* multiplicity (teams).
MedicalStaff carries contact information for notifications.
Design Decisions & Extensibility

Separation of Concerns: Rule evaluation (ThresholdRule), orchestration (AlertGenerator), and dispatch (AlertManager) are separated to simplify unit testing and future extension.
Per-Patient Thresholds: The thresholds: Map<String,List<ThresholdRule>> in AlertGenerator supports customized alert profiles.
Strategy-Friendly: To add new alert strategies (e.g., composite rules), one could extend ThresholdRule or inject a new rule checker without modifying existing classes.
Scalability: By delegating delivery to AlertManager, additional channels (e.g., pager, dashboard) can be plugged in easily.