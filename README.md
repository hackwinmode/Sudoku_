# Sudoku_

![Home](https://i.imgur.com/pXMnNqf.png)

Các tính năng đã làm:
- Em không làm thuật toán sinh board mà xây dựng class Data có property gồm 2 mảng:
    + một mảng chứa board quizze.
    + một mảng chứa board solution.
- Người chơi chọn bản game, load dữ liệu board quizze từ class Data.
![Chọn ô](https://i.imgur.com/C2hqvjF.png)

- Dùng canvas để vẽ board game, nếu ô nhập là đúng thì số sẽ có màu xanh, sai có màu đỏ. 
![Chọn sai](https://i.imgur.com/w1LnDIo.png)

![Chọn đúng](https://i.imgur.com/2NBhjpS.png)
- Khi end game, ta sẽ so sánh dữ liệu trong board người chơi với board solution trong data được chọn 
    -> thông báo kết thúc game và chuyển về main activity.

Các tính năng chưa làm:
- Lưu board game đang chơi (dự tính dùng sqlite hoặc saved preferences) nhưng em lười viết tiếp. 




