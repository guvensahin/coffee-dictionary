# Kahve Sözlüğü İçerik Geliştirme Planı

Kullanıcı, `data_entry.csv` dosyasındaki tanımların geliştirilmesini ve yeni terimler eklenmesini istedi. Mevcut sözlükte bazı temel terimlerin (Robusta, French Press, Nitelikli Kahve vb.) eksik olduğu veya daha detaylı açıklanabileceği tespit edildi.

## Proposed Changes

### [MODIFY] [data_entry.csv](file:///C:/Users/guven/StudioProjects/coffee-dictionary/app/src/main/assets/data_entry.csv)

Aşağıdaki yeni terimler eklenecek ve mevcut bazı tanımlar zenginleştirilecek:

#### Yeni Eklenecek Terimler:
- **Nitelikli Kahve (Specialty Coffee)** (Kategori 1): Kalite standartları ve puanlaması hakkında bilgi.
- **Üçüncü Dalga (Third Wave)** (Kategori 1): Kahve kültüründeki değişim.
- **Robusta** (Kategori 2): Arabica ile farkları ve özellikleri.
- **Geisha (Gesha)** (Kategori 2): En prestijli kahve varyetelerinden biri.
- **Honey Process** (Kategori 2): Yarı yıkanmış işleme yöntemi.
- **Natural Process** (Kategori 2): Doğal/Güneşte kurutma yöntemi.
- **Washed Process** (Kategori 2): Yıkanmış işleme yöntemi.
- **French Press** (Kategori 5): En yaygın demleme yöntemlerinden biri.
- **Cold Brew** (Kategori 5/6): Soğuk demleme tekniği ve içeceği.
- **First Crack (İlk Çatlama)** (Kategori 7): Kavurma aşamasındaki kritik an.
- **Second Crack (İkinci Çatlama)** (Kategori 7): Koyu kavurma aşaması.
- **Maillard Reaksiyonu** (Kategori 7): Tat oluşumundaki kimyasal süreç.
- **Gövde (Body)** (Kategori 9): Ağızda bıraktığı ağırlık hissi.
- **Parlaklık (Brightness)** (Kategori 9): Canlı asiditeyi tanımlayan terim.
- **Topraksı (Earthy)** (Kategori 9): Özellikle Endonezya kahvelerinde görülen tat notası.

## Verification Plan

### Manual Verification
* CSV dosyasının formatının (noktalı virgül ayracı) bozulmadığı kontrol edilecek.
* Uygulama içinde yeni eklenen terimlerin listede göründüğü ve doğru kategorize edildiği doğrulanacak.
