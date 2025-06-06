package com.example.taskmanagement.entity;

public enum PermissionName {
	// Task Permissions
    READ_TASK("Görev Okuma", "Görevleri görüntüleme", "task", "read"),
    CREATE_TASK("Görev Oluşturma", "Yeni görev oluşturma", "task", "create"),
    UPDATE_TASK("Görev Güncelleme", "Görev düzenleme", "task", "update"),
    DELETE_TASK("Görev Silme", "Görev silme", "task", "delete"),
    ASSIGN_TASK("Görev Atama", "Görev atama/çıkarma", "task", "assign"),

    // Project Permissions
    READ_PROJECT("Proje Okuma", "Projeleri görüntüleme", "project", "read"),
    CREATE_PROJECT("Proje Oluşturma", "Yeni proje oluşturma", "project", "create"),
    UPDATE_PROJECT("Proje Güncelleme", "Proje düzenleme", "project", "update"),
    DELETE_PROJECT("Proje Silme", "Proje silme", "project", "delete"),
    MANAGE_PROJECT_MEMBERS("Proje Takım Yönetimi", "Proje takımı düzenleme", "project", "manage_members"),

    // User Management Permissions
    READ_USER("Kullanıcı Okuma", "Kullanıcıları görüntüleme", "user", "read"),
    CREATE_USER("Kullanıcı Oluşturma", "Yeni kullanıcı oluşturma", "user", "create"),
    UPDATE_USER("Kullanıcı Güncelleme", "Kullanıcı bilgileri düzenleme", "user", "update"),
    DELETE_USER("Kullanıcı Silme", "Kullanıcı silme", "user", "delete"),
    MANAGE_USER_ROLES("Kullanıcı Rol Yönetimi", "Kullanıcı rollerini düzenleme", "user", "manage_roles"),

    // Dashboard Permissions
    VIEW_DASHBOARD("Dashboard Görüntüleme", "Dashboard görme", "dashboard", "view"),
    VIEW_ALL_TASKS("Tüm Görevleri Görüntüleme", "Tüm projelerdeki görevleri görme", "dashboard", "view_all_tasks"),
    VIEW_REPORTS("Rapor Görüntüleme", "Raporları görüntüleme", "dashboard", "view_reports");

    private final String displayName;
    private final String description;
    private final String resource;
    private final String action;

    PermissionName(String displayName, String description, String resource, String action) {
        this.displayName = displayName;
        this.description = description;
        this.resource = resource;
        this.action = action;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getResource() {
        return resource;
    }

    public String getAction() {
        return action;
    }
}
