#include<DxLib.h>
#include<math.h>
#define SHOT 2
#define SHIKAKU 400
#define ETAMA 400

int framecounter = 0, a = 350, life = 1, flag;
//SHIKAKU - a = enemy

typedef enum {
	eScene_Menu,
	eScene_WinMain,
	eScene_Setting1,
	eScene_Setting2,
} eScene;
static int Scene = eScene_Menu;

struct Shikaku {
	int x, y = { 0 }, muki, DamageFlag = { 0 }, DamageCounter;
};

struct Ball {
	int x, y, Counter = { 0 }, Damage = { 0 };
};

struct Shot {
	int x, y, Flag = { 0 };
};

struct EnemyShot {
	int x, y, Flag = { 0 }, Counter, Sx, Sy;
};

struct Shikaku S[SHIKAKU];
struct Ball B;
struct Shot Shot[SHOT];
struct EnemyShot ES[ETAMA];

int Ballatari(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh, int bc);
void dainyu(int a[], int  b, int c);
void shikaku(int i, int j, int Sw, Shikaku S[]);
void UpdateScene();
void Menu();
void Setting1();
void Setting2();
void SceneCase();

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow) {
	ChangeWindowMode(TRUE), DxLib_Init(), SetDrawScreen(DX_SCREEN_BACK), SetWindowUserCloseEnableFlag(FALSE);

	int Bw, Bh, Sw, Sh, ShotW, ShotH, ETamaW, ETamaH;
	int SikakuGraph, SikakuDamageGraph, BallGraph, ShotGraph, ETamaGraph;
	int ShotBFlag, i, j, k, l, ShikakuCounter;

	if (DxLib_Init() == -1)return -1;
	SceneCase();

	while (1) {
		if (flag == 1)break;
		B.Counter = 0;
		B.Damage = 0;

		for (i = 0; i < SHIKAKU; i++) {
			S[i].y = 0;
			S[i].DamageFlag = 0;
		}

		for (i = 0; i < SHOT; i++) {
			Shot[i].Flag = 0;
		}

		for (i = 0; i < ETAMA; i++) {
			ES[i].Flag = 0;
		}

		flag = 0;
		ShotBFlag = 0;
		k = 200;
		l = 3;

		//��ʐݒ�
		SetGraphMode(640, 480, 16);
		SetDrawScreen(DX_SCREEN_BACK);

		//�{�[���N�O���t�B�b�N�A�����ʒu�ݒ�
		BallGraph = LoadGraph("Ball.png");
		B.x = 288; B.y = 400;
		GetGraphSize(BallGraph, &Bw, &Bh);

		//�l�p�N�O���t�B�b�N�ݒ�
		SikakuDamageGraph = LoadGraph("SikakuDam.png");
		SikakuGraph = LoadGraph("Sikaku.png");
		GetGraphSize(SikakuGraph, &Sw, &Sh);
		//�l�p�N�̈ړ������A�����ʒu�Z�b�g
		for (i = 0; i < SHIKAKU - a; i++) {
			S[i].muki = 1;
			S[i].x = i * 100;
			S[i].y -= i / 4 * 60;
		}

		//�G�̒e�O���t�B�b�N�ݒ�
		ETamaGraph = LoadGraph("EShot.png");
		GetGraphSize(ETamaGraph, &ETamaW, &ETamaH);
		//�G���e�����^�C�~���O����邽�߂̌v���p�ϐ���-150����
		//-150�F�J�n���b�͓G�̒e�����˂��Ȃ�����
		for (i = 0; i < ETAMA - a; i++) {
			ES[i].Counter = -150;
		}

		//�e�̃O���t�B�b�N�ݒ�
		ShotGraph = LoadGraph("Shot.png");
		GetGraphSize(ShotGraph, &ShotW, &ShotH);

		//framecounter���Z�b�g
		framecounter = 0;

		//�ړ����[�`��
		while (ProcessMessage() == 0)
		{

			ClearDrawScreen();
			//�{�[���N����
			if (CheckHitKey(KEY_INPUT_UP) == 1)B.y -= 3;
			if (CheckHitKey(KEY_INPUT_DOWN) == 1)B.y += 3;
			if (CheckHitKey(KEY_INPUT_LEFT) == 1)B.x -= 3;
			if (CheckHitKey(KEY_INPUT_RIGHT) == 1)B.x += 3;
			//�X�y�[�X�L�[���������ꍇ�͏����𕪊�
			if (CheckHitKey(KEY_INPUT_SPACE)) {
				//�O�t���[���ŃV���b�g�{�^���������������ۑ�����Ă���ϐ���0��������e�𔭎�
				if (ShotBFlag == 0) {
					//��ʏ�ɏo�Ă��Ȃ��e�����邩�A�e�̐������J��Ԃ��Ē��ׂ�
					for (i = 0; i < SHOT; i++) {
						//�ei����ʏ�ɏo�Ă��Ȃ��ꍇ�͂��̒e����ʂɏo��
						if (Shot[i].Flag == 0) {
							//�ei�̈ʒu���Z�b�g�A�ʒu�̓{�[���N�̒��S�ɂ���
							Shot[i].x = (Bw - ShotW) / 2 + B.x;
							Shot[i].y = (Bh - ShotH) / 2 + B.y;
							//�ei�͌����_�������đ��݂���̂ŁA���ݏ�Ԃ�ێ�����ϐ��ɂP��������
							Shot[i].Flag = 1;
							//��e���o�����̂Œe���o�����[�v���甲����
							break;
						}
					}
				}
				//�O�t���[���ŃV���b�g�{�^����������Ă�������ۑ�����ϐ���1(������Ă���)����
				ShotBFlag = 1;
			}
			else {
				//�V���b�g�{�^����������Ă��Ȃ������ꍇ��
				//�O�t���[���ŃV���b�g�{�^����������Ă�������ۑ�����ϐ���0(������Ă��Ȃ�)����
				ShotBFlag = 0;
			}

			//�{�[���N����ʒ[����͂ݏo�����ɂȂ��Ă������ʓ��̍��W�ɖ߂��Ă�����
			if (B.x < 0)B.x = 0;
			if (B.x > 640 - 64)B.x = 640 - 64;
			if (B.y < 0)B.y = 0;
			if (B.y > 480 - 64)B.y = 480 - 64;

			//�{�[���N�̃��C�t�v��
			if (B.Counter == 1) {
				B.Damage++;
				if (life - B.Damage < 0) {
					while (1) {
						if (flag == 2)break;
						SetFontSize(30);
						DrawFormatString(175, 200, GetColor(255, 255, 255), "G A M E    O V E R");
						SetFontSize(20);
						DrawFormatString(250, 270, GetColor(255, 255, 255), "�hM �h= Menu");
						ScreenFlip();
						if (CheckHitKey(KEY_INPUT_M)) {
							flag = 2;
							Scene = eScene_Menu;
							break;
						}
					}
				}
			}
			if (flag == 2) {
				SceneCase();
				break;
			}
			SetFontSize(20);
			DrawFormatString(10, 450, GetColor(255, 255, 255), "Ball Life:%d", life - B.Damage);

			//�{�[���N��`��
			//DrawGraph(B.x, B.y, BallGraph, FALSE);

			if (B.Counter >= 1 && B.Counter <= 30) {
				if (B.Counter % 2 == 1)DrawGraph(B.x, B.y, BallGraph, FALSE);
				B.Counter++;
			}
			else if (B.Counter == 31) {
				B.Counter = 0;
			}
			else DrawGraph(B.x, B.y, BallGraph, FALSE);

			//�e�̐������e�𓮂����������J��Ԃ�
			for (i = 0; i < SHOT; i++) {
				//���@�̒ei�̈ړ����[�`��(���ݏ�Ԃ�ێ����Ă���ϐ��̓��e��1(���݂���)�̏ꍇ�̂ݍs��)
				if (Shot[i].Flag == 1) {
					//�e��16�h�b�g��Ɉړ�������
					Shot[i].y -= 16;
					//��ʊO�ɏo�Ă��܂����ꍇ�͑��ݏ�Ԃ�ێ����Ă���ϐ���0(���݂��Ȃ�)��������
					if (Shot[i].y < -80) {
						Shot[i].Flag = 0;
					}
					//��ʂɒe��`�悷��
					DrawGraph(Shot[i].x, Shot[i].y, ShotGraph, FALSE);
				}
			}
			//�l�p�N�̈ړ����[�`��
			//flameCounter�̒l�ɉ����Ďl�p�NY���W�ړ�
			//���񂾂�Y���ړ����x�㏸
			if (framecounter >= 200 && framecounter == k) {
				if (l < 100) {
					k = framecounter + 115 - l;
					l += 10;
				}
				else {
					k = framecounter + 25;
				}
				for (i = 0; i < SHIKAKU - a; i++) {
					S[i].y += 10;
				}
			}

			//�Q�[���N���A�w�W�̂���ShikakuCounter�Ŏl�p�̐����v��
			ShikakuCounter = 0;

			for (i = 0; i < SHIKAKU - a; i++) {
				//���c�߂Ă��邩�ǂ����ŏ����𕪊�
				if (S[i].DamageFlag == 1) {
					//���c�߂Ă���ꍇ�̓_���[�W���̃O���t�B�b�N��`�悷��
					DrawGraph(S[i].x, S[i].y, SikakuDamageGraph, FALSE);
					//���c�߂Ă��鎞�Ԃ�}��J�E���^�[��1�����Z����
					S[i].DamageCounter++;
					//�������c�ߎn�߂�30�t���[���o�߂��Ă�����l�p�N���ł̂��߃_���[�W�t���O�֐���2����
					if (S[i].DamageCounter == 30) {
						//���ł�\��2����
						S[i].DamageFlag = 2;
						ES[i].Flag = 0;
					}

					//�l�p�N���m���Ԃ�������A�ړ���������𔽓]����
					for (j = 0; j < SHIKAKU - a; j++) {
						shikaku(i, j, Sw, S);
					}
					ShikakuCounter++;
				}
				else if (S[i].DamageFlag == 0) {
					//�c��ł��Ȃ��ꍇ�͍��܂Œʂ�̏���
					//�l�p�N�̍��W���ړ����Ă�������Ɉړ�����
					if (S[i].muki == 1)S[i].x += 3 * i % 8 + 3;
					if (S[i].muki == 0)S[i].x -= 3 * i % 8 + 3;
					//�l�p�N����ʉE�[����o�����ɂȂ��Ă������ʓ��̍��W�ɖ߂��Ă����A�ړ�������������]����
					if (S[i].x > 576)
					{
						S[i].x = 576;
						S[i].muki = 0;
					}
					//�l�p�N����ʍ��[����o�����ɂȂ��Ă������ʓ��̍��W�ɖ߂��Ă����A�ړ�������������]����
					if (S[i].x < 0)
					{
						S[i].x = 0;
						S[i].muki = 1;
					}
					//�l�p�N���m���Ԃ�������A�ړ���������𔽓]����
					for (j = 0; j < SHIKAKU - a; j++) {
						shikaku(i, j, Sw, S);
						//�l�p�N��`��
						DrawGraph(S[i].x, S[i].y, SikakuGraph, FALSE);
					}
					if (B.Counter <= 30) {
						//�e�����^�C�~���O����邽�߂̃J�E���^�[��1�𑫂�
						ES[i].Counter++;
						//�����_���ȃ^�C�~���O�Œe�����������s��
						if (ES[i].Counter == i * i % 20 + 40) {
							//�������ɒe���w���ł��Ȃ��x��Ԃ������ꍇ�̂ݔ��ˏ������s��
							if (ES[i].Flag == 0) {
								//�e�̔��ˈʒu��ݒ肷��
								ES[i].x = S[i].x + Sw / 2 - ETamaW / 2;
								ES[i].y = S[i].y + Sw * 2 / 3 - ETamaH / 2;
								//�e�̈ړ����x��ݒ肷��
								{
									double sb, sbx, sby, bx, by, sx, sy;

									sx = ES[i].x + ETamaW / 2;
									sy = ES[i].y + ETamaH / 2;
									bx = B.x + Bw / 2;
									by = B.y + Bh / 2;
									sbx = bx - sx;
									sby = by - sy;
									sb = sqrt(sbx * sbx + sby * sby);

									//1�t���[��������8�h�b�g�ړ�����悤�ɂ���
									ES[i].Sx = sbx / sb * 10;
									ES[i].Sy = sby / sb * 10;
								}
								//�e�̏�Ԃ�ێ�����ϐ��Ɂw���ł���x������1��������
								ES[i].Flag = 1;
							}
							//�e�����^�C�~���O���v�����邽�߂̕ϐ���0����
							ES[i].Counter = 0;
						}
					}
					//�G�̒e�̏�Ԃ��w���ł���x�ꍇ�̂ݒe�̈ړ��������s��
					if (ES[i].Flag == 1) {
						ES[i].x += ES[i].Sx;
						ES[i].y += ES[i].Sy;
						//�����e����ʂ���͂ݏo���Ă��܂����ꍇ�͒e�̏�Ԃ��w���ł��Ȃ��x��\��0�ɂ���
						if (ES[i].y > 480 || ES[i].y < 0 || ES[i].x>640 || ES[i].x < 0)ES[i].Flag = 0;
						//��ʂɕ`�悷��(ETamaGraph:�G�̒e�̃O���t�B�b�N�̃n���h��)
						DrawGraph((int)ES[i].x, (int)ES[i].y, ETamaGraph, FALSE);
					}
					if (S[i].y > 480)ShikakuCounter++;
				}
				//S[i].DamageFlag == 2�̏ꍇ�͏��ł����l�p�N����ʊO��
				else {
					S[i].x = 640;
					ShikakuCounter++;
				}
			}
			if (ShikakuCounter == SHIKAKU - a) {
				while (1) {
					ClearDrawScreen();
					SetFontSize(35);
					DrawFormatString(135, 225, GetColor(255, 255, 0), "G A M E    C L E A R");
					SetFontSize(20);
					DrawFormatString(250, 300, GetColor(255, 255, 0), "�hM �h= Menu");
					ScreenFlip();
					if (CheckHitKey(KEY_INPUT_M)) {
						flag = 2;
						Scene = eScene_Menu;
						break;
						//i = 400;

					}
				}
			}
			if (flag == 2) {
				SceneCase();
				break;
			}

			//�e�Ǝl�p�N�̓����蔻��A�e�̐������J��Ԃ�
			for (i = 0; i < SHOT; i++) {
				//�ei�����݂��Ă���ꍇ�̂ݎ��̏����Ɉڂ�
				if (Shot[i].Flag == 1) {
					//�l�p�N�Ƃ̓����蔻��
					for (j = 0; j < SHIKAKU - a; j++) {
						if (S[j].DamageFlag == 0) {
							S[j].DamageFlag = Ballatari(Shot[i].x, Shot[i].y, ShotW, ShotH, S[j].x, S[j].y, Sw, Sh, S[j].DamageFlag);
							if (S[j].DamageFlag == 1) {
								Shot[i].Flag = 0;
								S[j].DamageCounter = 0;
							}
						}
					}
				}
			}

			//�l�p�N�ƃ{�[���N�̓����蔻��
			for (i = 0; i < SHIKAKU - a; i++) {
				B.Counter = Ballatari(S[i].x, S[i].y, Sw, Sh, B.x, B.y, Bw, Bh, B.Counter);

			}

			//ETama�ƃ{�[���N�̓����蔻��
			for (i = 0; i < ETAMA - a; i++) {
				//ETamai�����݂��Ă���ꍇ�̂ݎ��̏����Ɉڂ�
				if (ES[i].Flag == 1) {
					B.Counter = Ballatari(ES[i].x, ES[i].y, ETamaW, ETamaH, B.x, B.y, Bw, Bh, B.Counter);
				}
			}

			if (CheckHitKey(KEY_INPUT_ESCAPE)) {
				flag = 1;
				break;
			}

			//����ʂ̓��e��\��ʂɃR�s�[����
			ScreenFlip();
			if (ProcessMessage() < 0)return 0;

			framecounter++;
		}
		if (flag == 1)break;
	}

	DxLib_End();
	return 0;
}

//�z��ɂ܂Ƃ߂Ēl����
void dainyu(int a[], int  b, int c) {
	int i;
	for (i = 0;i < b;i++) {
		a[i] = c;
	}
}

//�����蔻��
int Ballatari(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh, int bc) {
	if (((ax  >bx&& ax + 5 < bx + bw - 10) ||
		(bx > ax && bx + 5 < ax + aw - 10)) &&
		((ay > by&& ay + 5 < by + bh - 10) ||
			(by > ay && by + 5 < ay + ah - 10)))
	{
		if (bc == 0)return 1;
		else return bc;
	}
	else return bc;
}

//�l�p�Ԃ��蔻��
void shikaku(int i, int j, int Sw, Shikaku S[]) {

	if (i == j)return;
	if (S[i].y == S[j].y) {
		if (S[j].x < S[i].x && S[i].x < S[j].x + Sw - 5) {
			if (S[i].muki == 0) {
				S[i].x = S[j].x + Sw - 5;
				S[i].muki = 1;
				S[j].muki = 0;
			}
			else {
				S[i].x = S[j].x - Sw + 5;
				S[i].muki = 0;
				S[j].muki = 1;
			}
		}
	}
}

//��ʑJ��
void SceneCase() {

	while (ScreenFlip() == 0 && ProcessMessage() == 0 && ClearDrawScreen() == 0) {
		framecounter++;

		switch (Scene) {
		case eScene_Menu:
			Menu();
			break;
		case eScene_WinMain:
			return;
		case eScene_Setting1:
			Setting1();
			break;
		case eScene_Setting2:
			Setting2();
			break;
		}

		if (CheckHitKey(KEY_INPUT_ESCAPE)) {
			flag = 1;
			return;
		}

		UpdateScene();
	}

}

//��ʑJ�ځi���̓L�[�ݒ�j
void UpdateScene() {

	if (CheckHitKey(KEY_INPUT_M) != 0) {
		Scene = eScene_Menu;
	}
	if (CheckHitKey(KEY_INPUT_SPACE) != 0) {
		Scene = eScene_WinMain;
	}
	if (CheckHitKey(KEY_INPUT_S) != 0) {
		Scene = eScene_Setting1;
	}
	if (Scene == eScene_Setting1&&CheckHitKey(KEY_INPUT_RIGHT) != 0) {
		Scene = eScene_Setting2;
	}
	if (Scene == eScene_Setting2&&CheckHitKey(KEY_INPUT_LEFT) != 0) {
		Scene = eScene_Setting1;
	}

}

//�^�C�g�����
void Menu() {
	int BallGraph, SikakuGraph;
	BallGraph = LoadGraph("Ball.png");
	SikakuGraph = LoadGraph("Sikaku.png");

	DrawRotaGraph(170, 160, 4, 0, BallGraph, FALSE);
	DrawRotaGraph(470, 160, 3.5, 0, SikakuGraph, FALSE);

	//�uPress SPACE to START�v�_�ŕ\��
	if (framecounter / 25 % 2 == 1);
	else {
		SetFontSize(27);
		DrawString(170, 300, "Press SPACE to START", GetColor(225, 225, 150));
	}
	SetFontSize(18);
	DrawString(235, 350, "�hS �h= Setting", GetColor(255, 255, 255));
}

//�G���ݒ���
void Setting1() {
	int enemy = SHIKAKU - a;
	SetFontSize(35);
	DrawString(213, 80, "Setting(1/2)", GetColor(255, 255, 255));
	SetFontSize(25);
	DrawString(190, 205, "Enemy(8�`400):", GetColor(255, 255, 255));
	DrawString(470, 430, "Next Page��", GetColor(255, 255, 255));
	SetFontSize(15);
	DrawString(195, 260, "�hControl �h= Enemy Control", GetColor(255, 255, 255));
	SetFontSize(23);
	DrawString(155, 350, "Press SPACE to GAME START", GetColor(225, 225, 150));
	if (CheckHitKey(KEY_INPUT_LCONTROL) == 1 || CheckHitKey(KEY_INPUT_RCONTROL) == 1) {
		SetFontSize(25);
		enemy = KeyInputNumber(400, 205, 400, 8, TRUE);
		a = 400 - enemy;
	}
	else {
		SetFontSize(25);
		DrawFormatString(400, 205, GetColor(255, 255, 255), "%d", enemy);
	}

}

//���C�t�ݒ���
void Setting2() {
	SetFontSize(35);
	DrawString(213, 80, "Setting(2/2)", GetColor(255, 255, 255));
	SetFontSize(25);
	DrawString(210, 205, "Life(1�`200):", GetColor(255, 255, 255));
	DrawString(10, 430, "��Prev Page", GetColor(255, 255, 255));
	SetFontSize(15);
	DrawString(200, 260, "�hControl �h= Life Control", GetColor(255, 255, 255));
	SetFontSize(23);
	DrawString(155, 350, "Press SPACE to GAME START", GetColor(225, 225, 150));
	if (CheckHitKey(KEY_INPUT_LCONTROL) == 1 || CheckHitKey(KEY_INPUT_RCONTROL) == 1) {
		SetFontSize(25);
		life = KeyInputNumber(400, 205, 200, 1, TRUE);
	}
	else {
		SetFontSize(25);
		DrawFormatString(400, 205, GetColor(255, 255, 255), "%d", life);
	}

}
