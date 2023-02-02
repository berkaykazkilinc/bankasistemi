Bu projede bir bankanın yönetim sistemi için bir veritabanı tasarlanmış ve bu veritabanının üzerinde
gerekli işlemleri gerçekleyen bir uygulama geliştirilmiştir.

Veri tabanındaki tüm tablolar en az 3NF formatındadır

Projede Java,Swing teknolojileri ve veritabanında PostgreSQL kullanılmıştır.


# Sistemdeki Roller
## ● Müşteriler
- Hesaplarından para çekebilirler ve yatırabilirler.
- Yeni hesap açma ve var olan bir hesabı silme talebinde bulunabilirler.
   - Bakiyesi “0” olmayan bir hesap silinemez.
- Birbirleri arasında para transferi yapabilirler.
   - Farklı para birimlerine sahip hesaplar arası transferler sırasında gönderilen miktar
hedef para birimine otomatik olarak çevrilmelidir.
- Bilgilerini güncelleyebilirler. (Adres, Telefon vs.)
- Bankaya para transferi yapabilirler. (Kredi borcu ödeme)
- Bankadan kredi talep edebilirler. 
   - Kredi sadece TL cinsinden talep edilebilmektedir.
  - Bankanın kredi talebini onaylaması durumunda istenilen vade oranınca (faiz ve
anapara toplamı) bölünerek aylara borç olarak yansıtılır.
  - Aylık özet görüntülemede kredi borcu ödemeleri için ödenen faiz ve anapara ayrı
ayrı görüntülenmelidir.
  - Müşterinin aylık borcunun tamamını ödememesi durumunda kalan borç ek faiz
hesaplanarak bir sonraki aya devreder.
  - Faiz ve gecikme faiz oranı banka müdürü tarafından belirlenir.
  - Aylık borç ve kalan borç ayrı ayrı görüntülenmeli. (müşteri isterse tüm borcunu
tek seferde ödeyebilir)
  - Erken ödeme durumlarında gelecek aylar için faiz alınmayacaktır.
  
- Aylık özetlerini görüntüleyebilirler. (Geçerli ay içerisinde yaptığı para gönderme, çekme,
kredi borcu ödeme gibi işlemlerin özeti)

## ● Banka Müdürü
- Bankanın genel durumunu (gelir, gider, kar ve toplam bakiye) görüntüleyebilmektedir.
- Yeni para birimi (Dolar, Euro, Sterling vs.) ekleyebilir ve kur değerlerini güncelleyebilir.
- Çalışanların maaş ücretlerini belirleyebilecektir.
  - Tek bir çalışan türü vardır (müşteri temsilcisi). Hepsinin maaş miktarı aynıdır.
- Kredi ve gecikme faiz oranını belirler.
- Müşteri ekleyebilir.
  - Sisteme yeni bir müşteri eklenmesi durumunda en az müşteriye sahip olan
temsilciye atanır.
- Bankada gerçekleşen tüm işlemleri (para çekme, yatırma ve transfer)
görüntüleyebilmektedir.
  - İşlemleri listelerken “son X adet işlemi listele” şeklinde bir seçenek sunulmalıdır.

## ● Müşteri Temsilcisi
- Her müşterinin bir temsilcisi vardır.
- Müşteri ekleme, silme ve düzenleme yapabilir (silme ve düzenleme işlemleri sadece kendi
müşterileri için geçerlidir).
- Müşteri bilgilerini güncelleyebilirler. (Adres, Telefon vs.)
- İlgilendikleri müşterilerin genel durumlarını (gelir, gider ve toplam bakiye)
görüntüleyebilmektedir.
- Müşterilerden gelen hesap açma, silme ve kredi taleplerini görüntüleme ve onaylama
sorumluluğu temsilcilere aittir.
- İlgilendikleri müşterilerin işlemlerini (para çekme, yatırma ve transfer)
görüntüleyebilmektedir.
