```
Requirement :
1 User → max 3 locker aktif
Registrasi User: no HP, KTP, email
Deposit: 10.000 / locker / hari
Denda keterlambatan: 5.000 / hari
Locker:
Punya password
Password hanya bisa dipakai 2 kali (simpan & ambil)
Salah password 3x → locker hangus 
Setelah booking → kirim email berisi nomor locker & password
Deposit:
Dikembalikan saat selesai
Dipotong denda
Jika denda > deposit → user bayar selisih
jika ada penalty salah password 3x deposit juga hangus
Locker yang sama dipinjam ulang → password baru
```

Tech
```
Java 17 
Spring boot 3.x
database : postgres , H2 ( di command di application properties dan POM.XML) 
```

Runing application

open cmd
```
git clone ttps://github.com/asep13009/LockerApplication.git
cd LockerApplication
mvn clean install
mvn spring-boot:run
```
silahkan coba 
tempel di browser
endpoint ini
http://localhost:8080/api/admin/lockers/all
jika muncul sukses di running

