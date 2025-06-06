package com.example.taskmanagement.entity;

public enum RoleName {
	ADMIN("Admin", "Tüm yetkilere sahip"),
    MANAGER("Yönetici", "Görev atama ve düzenleme yetkisi"),
    PROJECT_LEAD("Proje Lideri", "Proje yönetimi yetkisi"),
    DEVELOPER("Geliştirici", "Görev oluşturma ve düzenleme yetkisi"),
    TEAM_MEMBER("Takım Üyesi", "Atanan görevleri görme yetkisi"),
    USER("Kullanıcı", "Temel kullanıcı yetkileri");
    
    private final String displayName;
    private final String description;
    
    RoleName(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
