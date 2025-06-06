# Web Programming Project (Spring Boot)

Bu proje, Web Programming Project kapsamında geliştirilen Spring Boot tabanlı bir web uygulamasıdır. Proje, modern web geliştirme teknikleri ve Spring Boot framework kullanılarak hazırlanmıştır.

## İçindekiler
- [Genel Bakış](#genel-bakış)
- [Özellikler](#özellikler)
- [Teknolojiler](#teknolojiler)
- [Kurulum](#kurulum)
- [Çalıştırma](#çalıştırma)


## Genel Bakış
Bu proje, kullanıcı yönetimi, görev takibi ve rol tabanlı yetkilendirme gibi özelliklere sahip bir web uygulamasıdır.

## Özellikler
- Kullanıcı kayıt ve giriş işlemleri
- Rol tabanlı erişim kontrolü
- CRUD işlemleri (Kullanıcılar, Görevler vb.)
- REST API desteği
- JWT ile güvenlik

## Teknolojiler
- Java 17
- Spring Boot 3.x
- Spring Security
- JPA / Hibernate
- Maven
- H2 / PostgreSQL 
- Lombok

## Kurulum

1. Depoyu klonlayın:
   ```bash
   git clone https://github.com/Web-Programming-Project-SpringBoot/Web-Programming-Project.git

cd Web-Programming-Project

mvn clean install

mvn spring-boot:run

Çalıştırma
Uygulama http://localhost:8080 adresinde çalışır.

API endpointleri /api altında yer alır. Örnek: /api/users

Katkıda Bulunma
Katkıda bulunmak isterseniz:

Fork yapın

Yeni bir branch oluşturun (git checkout -b feature/ozellik)

Değişikliklerinizi commit edin (git commit -m "Yeni özellik eklendi")

Branch'i push edin (git push origin feature/ozellik)

Pull request açın

