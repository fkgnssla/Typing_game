# 바이러스 퇴치 게임
> 단어가 나타나면 타이핑하여 점수를 얻는 게임이고
> Java의 Swing을 이용하여 개발하였다.


# 개요
이 게임의 주요 시나리오는 나의 입속으로 들어오는 바이러스들을 막는 게임이다. 각 바이러스에는 단어가 적혀있고 
타이핑을 통해 단어를 맞추면 해당 바이러스가 퇴치된다. 초기 화면에서 입장하기를 눌러 입장하거나 종료하기를 눌러 종료할 수 있다. 
입장을 하면 배경음악이 나오며 이는 오른쪽 패널의 "배경음악 켜기", "배경음악 끄기" 버튼으로 제어할 수 있다. 메뉴바의 game 메뉴를 
클릭하면 종료하거나 단어를 추가할 수 있다. 그 옆에는 게임 시작 버튼, 게임 중단 버튼, 난이도가 구현되어 있다. 
난이도를 설정하여 기호에 맞게 즐길 수 있다. 난이도가 올라갈수록 바이러스가 빨리 내려오고 빨리 생성된다. 그리고 퇴치 시 점수를 더 얻는다. 
게임 시작 버튼을 눌러 시작하면 다른 배경음악이 나온다. 단어를 맞추면 효과음이 나오며 점수가 올라가는데 오타를 내지 않고 단어 4개 이상을 맞추는 경우
오른쪽 패널에 있는 이모티콘이 행복한 이모티콘으로 바뀌게 된다. 이때 단어를 맞추면 점수는 원점수의 2배씩 올라가게 된다. 이런 상태는 단어가 입안에 
떨어져서 없어지거나 오타를 내지만 않으면 유지된다. 게임 플레이 중에 각각 5% 확률로 마스크 아이템이나 눈 아이템이 뜰 수도 있다. 마스크 아이템을
타이핑해서 먹는다면 오른쪽 패널의 생명 개수 밑에 마스크의 개수가 표시된다. 마스크의 효과는 단어가 입안으로 떨어졌을 때 생명 대신에 마스크가
깎이고 이모티콘의 변화는 없다. 눈 아이템을 타이핑해서 먹으면 5초 동안 단어가 떨어지는 속도가 느려지게 되고 5초가 지나면 다시 원래 속도로 돌아온다.
메뉴바의 게임 중단 버튼을 누르거나 생명이 모두 없어지면 게임오버가 되는데 이때 효과음이 나오고 닉네임을 입력하면 랭킹에 입력되게 된다. 
오른쪽 밑 패널에는 상위 8명의 닉네임과 점수가 출력된다. 이후 "게임 종료", "한 판 더하기" 버튼 중 하나를 클릭하여 계속 게임을 진행하거나 게임을 종료할 수 있다.

# 주요 클래스 및 객체 구조도
![image](https://user-images.githubusercontent.com/92067099/174253342-ac598e10-6cea-488d-89f5-dc72c7210bba.png)


# 게임화면
### 시작화면
![image](https://user-images.githubusercontent.com/92067099/174250948-b442a2f3-7fe7-471c-89ee-c744849d5cf9.png)
![image](https://user-images.githubusercontent.com/92067099/174255264-446063dc-a8d7-418c-bca9-a9a5f9c2f617.png)


### 게임로비화면
![image](https://user-images.githubusercontent.com/92067099/174251321-534fcf65-e3a4-4e3a-98e6-e0fd7adaa8e7.png)
![image](https://user-images.githubusercontent.com/92067099/174255294-1010036c-a551-4eb3-ad5a-547f995ef9cd.png)


### 단어를 추가하는 과정
![image](https://user-images.githubusercontent.com/92067099/174251512-588c98d2-eb2f-4b41-9bd3-36ecd24f53c6.png)
![image](https://user-images.githubusercontent.com/92067099/174251560-656ffc15-5057-414b-b0b3-57b5ab8651f3.png)
![image](https://user-images.githubusercontent.com/92067099/174255307-7f4985e8-0090-494d-83cb-8e4e96ff8d7c.png)

### 난이도 설정
![image](https://user-images.githubusercontent.com/92067099/174251602-d222121e-66c3-45fd-8808-2cbb6bc23ada.png)
![image](https://user-images.githubusercontent.com/92067099/174255319-91b3f470-356d-4a23-bae1-ac82ee9cc24f.png)

### 게임실행화면
![image](https://user-images.githubusercontent.com/92067099/174251856-d4ae811f-b0ea-48ee-b8da-c20d48562286.png)
![image](https://user-images.githubusercontent.com/92067099/174255323-08e9ad84-ceaf-42de-aae1-01ddc92cc0fd.png)

### 오타를 내지않고 4회이상 맞춰 행복한 이모티콘으로 변한 화면
![image](https://user-images.githubusercontent.com/92067099/174251982-7c27fc57-5145-4bf5-b98b-5c36cd92b443.png)
![image](https://user-images.githubusercontent.com/92067099/174255330-3fa73cd2-ccbf-4bd6-94c3-3028b448ab18.png)

### 오타를 내어 이모티콘이 우는 이모티콘으로 변한 화면
![image](https://user-images.githubusercontent.com/92067099/174252082-28891c10-120c-4fa9-87b5-ecdb3dd987b6.png)
![image](https://user-images.githubusercontent.com/92067099/174255335-eadf2fba-bfbf-4e6f-93da-12dc57a38887.png)

### 게임오버
![image](https://user-images.githubusercontent.com/92067099/174252269-cab16a8d-8f57-4283-9e51-07f073180670.png)
![image](https://user-images.githubusercontent.com/92067099/174255339-30c65bd0-4e14-4ef5-ad7a-3f3fd31de01a.png)

### 닉네임을 입력해 랭킹이 갱신
![image](https://user-images.githubusercontent.com/92067099/174252353-cd5e4a56-072a-4036-a8e0-08332fca78ad.png)







