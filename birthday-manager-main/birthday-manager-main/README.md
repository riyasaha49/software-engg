# জন্মদিন পরিচালনা সিস্টেম (Birthday Manager)

একটি JavaFX ভিত্তিক ডেস্কটপ অ্যাপ্লিকেশন যা আপনার সহপাঠীদের জন্মদিন পরিচালনা করতে সাহায্য করে। এটি বাংলা ভাষায় ইউজার ইন্টারফেস প্রদান করে এবং MySQL ডেটাবেস ব্যবহার করে তথ্য সংরক্ষণ করে।

## বৈশিষ্ট্যসমূহ (Features)

### ✅ মূল বৈশিষ্ট্য
- **সহপাঠী যোগ করা**: নাম, জন্ম তারিখ, ইমেইল, ফোন নম্বর এবং মন্তব্য সহ সহপাঠীদের তথ্য যোগ করুন
- **তথ্য আপডেট**: বিদ্যমান সহপাঠীদের তথ্য সম্পাদনা করুন
- **তথ্য মুছে ফেলা**: অপ্রয়োজনীয় এন্ট্রি মুছে ফেলুন
- **আসন্ন জন্মদিন**: পরবর্তী ৩০ দিনের মধ্যে জন্মদিন দেখুন
- **আজকের জন্মদিন**: আজকের জন্মদিনের জন্য বিশেষ নোটিফিকেশন

### 🔍 অনুসন্ধান বৈশিষ্ট্য
- **নাম দিয়ে খোঁজা**: সহপাঠীর নাম দিয়ে অনুসন্ধান করুন
- **মাস দিয়ে খোঁজা**: নির্দিষ্ট মাসের জন্মদিন খুঁজুন
- **সমন্বিত অনুসন্ধান**: নাম এবং মাস একসাথে ব্যবহার করে অনুসন্ধান করুন

### 🔔 নোটিফিকেশন
- স্বয়ংক্রিয় জন্মদিনের অনুস্মারক
- আজকের জন্মদিনের জন্য পপআপ নোটিফিকেশন
- আসন্ন জন্মদিনের তালিকা

## প্রযুক্তি স্ট্যাক (Technology Stack)

- **Frontend**: JavaFX 17.0.2
- **Backend**: Java 11
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Database Connectivity**: JDBC (MySQL Connector 8.0.30)

## প্রয়োজনীয়তা (Requirements)

### সিস্টেম প্রয়োজনীয়তা
- Java 11 বা তার পরবর্তী সংস্করণ
- MySQL 8.0 বা তার পরবর্তী সংস্করণ
- Maven 3.6+
- কমপক্ষে 512MB RAM
- 100MB হার্ড ডিস্ক স্পেস

### ডেটাবেস সেটআপ
```sql
-- MySQL সার্ভার চালু করুন এবং নিম্নলিখিত কনফিগারেশন ব্যবহার করুন:
-- Host: localhost
-- Port: 3306
-- Username: root
-- Password: (খালি রাখুন বা আপনার পাসওয়ার্ড দিন)
```

## ইনস্টলেশন এবং সেটআপ (Installation & Setup)

### ১. প্রজেক্ট ডাউনলোড
```bash
git clone [your-repository-url]
cd birthday-manager
```

### ২. MySQL সেটআপ
1. MySQL সার্ভার চালু করুন
2. একটি নতুন ডেটাবেস তৈরি করুন (অ্যাপ্লিকেশন স্বয়ংক্রিয়ভাবে তৈরি করবে)

### ৩. JDBC ড্রাইভার যোগ করুন
1. `mysql-connector-java-8.0.30.jar` ফাইলটি ডাউনলোড করুন
2. এটি আপনার Maven local repository-তে ইনস্টল করুন:
```bash
mvn install:install-file -Dfile=mysql-connector-java-8.0.30.jar -DgroupId=mysql -DartifactId=mysql-connector-java -Dversion=8.0.30 -Dpackaging=jar
```

### ৪. প্রজেক্ট বিল্ড এবং রান
```bash
# প্রজেক্ট বিল্ড করুন
mvn clean compile

# অ্যাপ্লিকেশন চালান
mvn javafx:run

# অথবা JAR ফাইল তৈরি করুন
mvn clean package
java -jar target/birthday-manager-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## প্রজেক্ট কাঠামো (Project Structure)

```
birthday-manager/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/birthday/manager/
│       │       ├── MainApp.java                 # মূল অ্যাপ্লিকেশন ক্লাস
│       │       ├── controller/
│       │       │   └── MainController.java     # UI কন্ট্রোলার
│       │       ├── dao/
│       │       │   └── ClassmateDAO.java       # ডেটা অ্যাক্সেস অবজেক্ট
│       │       ├── database/
│       │       │   └── DatabaseManager.java    # ডেটাবেস ম্যানেজার
│       │       ├── model/
│       │       │   └── Classmate.java          # ডেটা মডেল
│       │       └── notification/
│       │           └── NotificationService.java # নোটিফিকেশন সার্ভিস
│       └── resources/
│           ├── css/
│           │   └── styles.css                  # CSS স্টাইল
│           ├── fxml/
│           │   └── main.fxml                   # UI লেআউট
│           └── images/
│               └── birthday-icon.png           # অ্যাপ আইকন
├── pom.xml                                     # Maven কনফিগারেশন
└── README.md                                   # এই ফাইল
```

## ডেটাবেস স্কিমা (Database Schema)

```sql
CREATE TABLE classmates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## ব্যবহারবিধি (Usage Guide)

### সহপাঠী যোগ করা
1. ডান পাশের ফর্মে সহপাঠীর তথ্য পূরণ করুন
2. "যোগ করুন" বাটনে ক্লিক করুন
3. সফল হলে তালিকায় নতুন এন্ট্রি দেখা যাবে

### তথ্য আপডেট করা
1. তালিকা থেকে একটি সহপাঠী নির্বাচন করুন
2. ফর্মে তথ্য সম্পাদনা করুন
3. "আপডেট করুন" বাটনে ক্লিক করুন

### অনুসন্ধান করা
1. উপরের অনুসন্ধান বার ব্যবহার করুন
2. নাম লিখুন বা মাস নির্বাচন করুন
3. "খুঁজুন" বাটনে ক্লিক করুন

### আসন্ন জন্মদিন দেখা
1. "আসন্ন জন্মদিন" বাটনে ক্লিক করুন
2. পরবর্তী ৩০ দিনের জন্মদিনের তালিকা দেখুন

## কনফিগারেশন (Configuration)

### ডেটাবেস কনফিগারেশন
`DatabaseManager.java` ফাইলে ডেটাবেস সেটিংস পরিবর্তন করুন:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/birthday_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = ""; // আপনার পাসওয়ার্ড দিন
```

### বাংলা ফন্ট সেটআপ
CSS ফাইলে বাংলা ফন্ট কনফিগারেশন:
```css
.root {
    -fx-font-family: "Segoe UI", "Noto Sans Bengali", "Kalpurush", Arial, sans-serif;
}
```

## সমস্যা সমাধান (Troubleshooting)

### সাধারণ সমস্যা

**১. ডেটাবেস সংযোগ ব্যর্থ**
- MySQL সার্ভার চালু আছে কিনা চেক করুন
- ইউজারনেম এবং পাসওয়ার্ড সঠিক আছে কিনা যাচাই করুন
- পোর্ট 3306 খোলা আছে কিনা দেখুন

**২. বাংলা ফন্ট দেখাচ্ছে না**
- সিস্টেমে বাংলা ফন্ট ইনস্টল করুন
- "Noto Sans Bengali" বা "Kalpurush" ফন্ট ডাউনলোড করুন

**৩. JavaFX রানটাইম ত্রুটি**
- Java 11+ ইনস্টল আছে কিনা চেক করুন
- JavaFX মডিউল path সঠিক আছে কিনা দেখুন

### লগ ফাইল
অ্যাপ্লিকেশন কনসোলে ত্রুটির বিস্তারিত তথ্য দেখুন:
```bash
mvn javafx:run > app.log 2>&1
```

## অবদান (Contributing)

এই প্রজেক্টে অবদান রাখতে চাইলে:

1. প্রজেক্টটি fork করুন
2. নতুন feature branch তৈরি করুন (`git checkout -b feature/AmazingFeature`)
3. আপনার পরিবর্তন commit করুন (`git commit -m 'Add some AmazingFeature'`)
4. Branch এ push করুন (`git push origin feature/AmazingFeature`)
5. Pull Request তৈরি করুন

## লাইসেন্স (License)

এই প্রজেক্টটি MIT লাইসেন্সের অধীনে বিতরণ করা হয়েছে। বিস্তারিত জানতে `LICENSE` ফাইল দেখুন।

## যোগাযোগ (Contact)

প্রজেক্ট লিঙ্ক: [https://github.com/yourusername/birthday-manager](https://github.com/yourusername/birthday-manager)

## কৃতজ্ঞতা (Acknowledgments)

- JavaFX কমিউনিটি
- MySQL ডেভেলপার টিম
- বাংলা ফন্ট ডেভেলপারগণ
- Maven এবং Apache সফটওয়্যার ফাউন্ডেশন

---

## স্ক্রিনশট (Screenshots)

### মূল ইন্টারফেস
- সহপাঠীদের তালিকা
- তথ্য এন্ট্রি ফর্ম
- অনুসন্ধান বার

### নোটিফিকেশন
- আজকের জন্মদিনের অ্যালার্ট
- আসন্ন জন্মদিনের তালিকা

---

**নোট**: এই অ্যাপ্লিকেশনটি শিক্ষামূলক উদ্দেশ্যে তৈরি করা হয়েছে। বাণিজ্যিক ব্যবহারের পূর্বে প্রয়োজনীয় লাইসেন্স এবং অনুমতি নিন।