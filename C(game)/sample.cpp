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

		//画面設定
		SetGraphMode(640, 480, 16);
		SetDrawScreen(DX_SCREEN_BACK);

		//ボール君グラフィック、初期位置設定
		BallGraph = LoadGraph("Ball.png");
		B.x = 288; B.y = 400;
		GetGraphSize(BallGraph, &Bw, &Bh);

		//四角君グラフィック設定
		SikakuDamageGraph = LoadGraph("SikakuDam.png");
		SikakuGraph = LoadGraph("Sikaku.png");
		GetGraphSize(SikakuGraph, &Sw, &Sh);
		//四角君の移動方向、初期位置セット
		for (i = 0; i < SHIKAKU - a; i++) {
			S[i].muki = 1;
			S[i].x = i * 100;
			S[i].y -= i / 4 * 60;
		}

		//敵の弾グラフィック設定
		ETamaGraph = LoadGraph("EShot.png");
		GetGraphSize(ETamaGraph, &ETamaW, &ETamaH);
		//敵が弾を撃つタイミングを取るための計測用変数に-150を代入
		//-150：開始数秒は敵の弾が発射しないため
		for (i = 0; i < ETAMA - a; i++) {
			ES[i].Counter = -150;
		}

		//弾のグラフィック設定
		ShotGraph = LoadGraph("Shot.png");
		GetGraphSize(ShotGraph, &ShotW, &ShotH);

		//framecounterリセット
		framecounter = 0;

		//移動ルーチン
		while (ProcessMessage() == 0)
		{

			ClearDrawScreen();
			//ボール君操作
			if (CheckHitKey(KEY_INPUT_UP) == 1)B.y -= 3;
			if (CheckHitKey(KEY_INPUT_DOWN) == 1)B.y += 3;
			if (CheckHitKey(KEY_INPUT_LEFT) == 1)B.x -= 3;
			if (CheckHitKey(KEY_INPUT_RIGHT) == 1)B.x += 3;
			//スペースキーを押した場合は処理を分岐
			if (CheckHitKey(KEY_INPUT_SPACE)) {
				//前フレームでショットボタンを押したかが保存されている変数が0だったら弾を発射
				if (ShotBFlag == 0) {
					//画面上に出ていない弾があるか、弾の数だけ繰り返して調べる
					for (i = 0; i < SHOT; i++) {
						//弾iが画面上に出ていない場合はその弾を画面に出す
						if (Shot[i].Flag == 0) {
							//弾iの位置をセット、位置はボール君の中心にする
							Shot[i].x = (Bw - ShotW) / 2 + B.x;
							Shot[i].y = (Bh - ShotH) / 2 + B.y;
							//弾iは現時点をもって存在するので、存在状態を保持する変数に１を代入する
							Shot[i].Flag = 1;
							//一つ弾を出したので弾を出すループから抜ける
							break;
						}
					}
				}
				//前フレームでショットボタンを押されていたかを保存する変数に1(おされていた)を代入
				ShotBFlag = 1;
			}
			else {
				//ショットボタンが押されていなかった場合は
				//前フレームでショットボタンを押されていたかを保存する変数に0(おされていない)を代入
				ShotBFlag = 0;
			}

			//ボール君が画面端からはみ出そうになっていたら画面内の座標に戻してあげる
			if (B.x < 0)B.x = 0;
			if (B.x > 640 - 64)B.x = 640 - 64;
			if (B.y < 0)B.y = 0;
			if (B.y > 480 - 64)B.y = 480 - 64;

			//ボール君のライフ計測
			if (B.Counter == 1) {
				B.Damage++;
				if (life - B.Damage < 0) {
					while (1) {
						if (flag == 2)break;
						SetFontSize(30);
						DrawFormatString(175, 200, GetColor(255, 255, 255), "G A M E    O V E R");
						SetFontSize(20);
						DrawFormatString(250, 270, GetColor(255, 255, 255), "”M ”= Menu");
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

			//ボール君を描画
			//DrawGraph(B.x, B.y, BallGraph, FALSE);

			if (B.Counter >= 1 && B.Counter <= 30) {
				if (B.Counter % 2 == 1)DrawGraph(B.x, B.y, BallGraph, FALSE);
				B.Counter++;
			}
			else if (B.Counter == 31) {
				B.Counter = 0;
			}
			else DrawGraph(B.x, B.y, BallGraph, FALSE);

			//弾の数だけ弾を動かす処理を繰り返す
			for (i = 0; i < SHOT; i++) {
				//自機の弾iの移動ルーチン(存在状態を保持している変数の内容が1(存在する)の場合のみ行う)
				if (Shot[i].Flag == 1) {
					//弾を16ドット上に移動させる
					Shot[i].y -= 16;
					//画面外に出てしまった場合は存在状態を保持している変数に0(存在しない)を代入する
					if (Shot[i].y < -80) {
						Shot[i].Flag = 0;
					}
					//画面に弾を描画する
					DrawGraph(Shot[i].x, Shot[i].y, ShotGraph, FALSE);
				}
			}
			//四角君の移動ルーチン
			//flameCounterの値に応じて四角君Y座標移動
			//だんだんY軸移動速度上昇
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

			//ゲームクリア指標のためShikakuCounterで四角の数を計測
			ShikakuCounter = 0;

			for (i = 0; i < SHIKAKU - a; i++) {
				//顔を歪めているかどうかで処理を分岐
				if (S[i].DamageFlag == 1) {
					//顔を歪めている場合はダメージ時のグラフィックを描画する
					DrawGraph(S[i].x, S[i].y, SikakuDamageGraph, FALSE);
					//顔を歪めている時間を図るカウンターに1を加算する
					S[i].DamageCounter++;
					//もし顔を歪め始めて30フレーム経過していたら四角君消滅のためダメージフラグ関数に2を代入
					if (S[i].DamageCounter == 30) {
						//消滅を表す2を代入
						S[i].DamageFlag = 2;
						ES[i].Flag = 0;
					}

					//四角君同士がぶつかったら、移動する方向を反転する
					for (j = 0; j < SHIKAKU - a; j++) {
						shikaku(i, j, Sw, S);
					}
					ShikakuCounter++;
				}
				else if (S[i].DamageFlag == 0) {
					//歪んでいない場合は今まで通りの処理
					//四角君の座標を移動している方向に移動する
					if (S[i].muki == 1)S[i].x += 3 * i % 8 + 3;
					if (S[i].muki == 0)S[i].x -= 3 * i % 8 + 3;
					//四角君が画面右端から出そうになっていたら画面内の座標に戻してあげ、移動する方向も反転する
					if (S[i].x > 576)
					{
						S[i].x = 576;
						S[i].muki = 0;
					}
					//四角君が画面左端から出そうになっていたら画面内の座標に戻してあげ、移動する方向も反転する
					if (S[i].x < 0)
					{
						S[i].x = 0;
						S[i].muki = 1;
					}
					//四角君同士がぶつかったら、移動する方向を反転する
					for (j = 0; j < SHIKAKU - a; j++) {
						shikaku(i, j, Sw, S);
						//四角君を描画
						DrawGraph(S[i].x, S[i].y, SikakuGraph, FALSE);
					}
					if (B.Counter <= 30) {
						//弾を撃つタイミングを取るためのカウンターに1を足す
						ES[i].Counter++;
						//ランダムなタイミングで弾を撃つ処理を行う
						if (ES[i].Counter == i * i % 20 + 40) {
							//もし既に弾が『飛んでいない』状態だった場合のみ発射処理を行う
							if (ES[i].Flag == 0) {
								//弾の発射位置を設定する
								ES[i].x = S[i].x + Sw / 2 - ETamaW / 2;
								ES[i].y = S[i].y + Sw * 2 / 3 - ETamaH / 2;
								//弾の移動速度を設定する
								{
									double sb, sbx, sby, bx, by, sx, sy;

									sx = ES[i].x + ETamaW / 2;
									sy = ES[i].y + ETamaH / 2;
									bx = B.x + Bw / 2;
									by = B.y + Bh / 2;
									sbx = bx - sx;
									sby = by - sy;
									sb = sqrt(sbx * sbx + sby * sby);

									//1フレーム当たり8ドット移動するようにする
									ES[i].Sx = sbx / sb * 10;
									ES[i].Sy = sby / sb * 10;
								}
								//弾の状態を保持する変数に『飛んでいる』を示す1を代入する
								ES[i].Flag = 1;
							}
							//弾を撃つタイミングを計測するための変数に0を代入
							ES[i].Counter = 0;
						}
					}
					//敵の弾の状態が『飛んでいる』場合のみ弾の移動処理を行う
					if (ES[i].Flag == 1) {
						ES[i].x += ES[i].Sx;
						ES[i].y += ES[i].Sy;
						//もし弾が画面からはみ出してしまった場合は弾の状態を『飛んでいない』を表す0にする
						if (ES[i].y > 480 || ES[i].y < 0 || ES[i].x>640 || ES[i].x < 0)ES[i].Flag = 0;
						//画面に描画する(ETamaGraph:敵の弾のグラフィックのハンドル)
						DrawGraph((int)ES[i].x, (int)ES[i].y, ETamaGraph, FALSE);
					}
					if (S[i].y > 480)ShikakuCounter++;
				}
				//S[i].DamageFlag == 2の場合は消滅した四角君を画面外へ
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
					DrawFormatString(250, 300, GetColor(255, 255, 0), "”M ”= Menu");
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

			//弾と四角君の当たり判定、弾の数だけ繰り返す
			for (i = 0; i < SHOT; i++) {
				//弾iが存在している場合のみ次の処理に移る
				if (Shot[i].Flag == 1) {
					//四角君との当たり判定
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

			//四角君とボール君の当たり判定
			for (i = 0; i < SHIKAKU - a; i++) {
				B.Counter = Ballatari(S[i].x, S[i].y, Sw, Sh, B.x, B.y, Bw, Bh, B.Counter);

			}

			//ETamaとボール君の当たり判定
			for (i = 0; i < ETAMA - a; i++) {
				//ETamaiが存在している場合のみ次の処理に移る
				if (ES[i].Flag == 1) {
					B.Counter = Ballatari(ES[i].x, ES[i].y, ETamaW, ETamaH, B.x, B.y, Bw, Bh, B.Counter);
				}
			}

			if (CheckHitKey(KEY_INPUT_ESCAPE)) {
				flag = 1;
				break;
			}

			//裏画面の内容を表画面にコピーする
			ScreenFlip();
			if (ProcessMessage() < 0)return 0;

			framecounter++;
		}
		if (flag == 1)break;
	}

	DxLib_End();
	return 0;
}

//配列にまとめて値を代入
void dainyu(int a[], int  b, int c) {
	int i;
	for (i = 0;i < b;i++) {
		a[i] = c;
	}
}

//当たり判定
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

//四角ぶつかり判定
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

//画面遷移
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

//画面遷移（入力キー設定）
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

//タイトル画面
void Menu() {
	int BallGraph, SikakuGraph;
	BallGraph = LoadGraph("Ball.png");
	SikakuGraph = LoadGraph("Sikaku.png");

	DrawRotaGraph(170, 160, 4, 0, BallGraph, FALSE);
	DrawRotaGraph(470, 160, 3.5, 0, SikakuGraph, FALSE);

	//「Press SPACE to START」点滅表示
	if (framecounter / 25 % 2 == 1);
	else {
		SetFontSize(27);
		DrawString(170, 300, "Press SPACE to START", GetColor(225, 225, 150));
	}
	SetFontSize(18);
	DrawString(235, 350, "”S ”= Setting", GetColor(255, 255, 255));
}

//敵数設定画面
void Setting1() {
	int enemy = SHIKAKU - a;
	SetFontSize(35);
	DrawString(213, 80, "Setting(1/2)", GetColor(255, 255, 255));
	SetFontSize(25);
	DrawString(190, 205, "Enemy(8～400):", GetColor(255, 255, 255));
	DrawString(470, 430, "Next Page→", GetColor(255, 255, 255));
	SetFontSize(15);
	DrawString(195, 260, "”Control ”= Enemy Control", GetColor(255, 255, 255));
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

//ライフ設定画面
void Setting2() {
	SetFontSize(35);
	DrawString(213, 80, "Setting(2/2)", GetColor(255, 255, 255));
	SetFontSize(25);
	DrawString(210, 205, "Life(1～200):", GetColor(255, 255, 255));
	DrawString(10, 430, "←Prev Page", GetColor(255, 255, 255));
	SetFontSize(15);
	DrawString(200, 260, "”Control ”= Life Control", GetColor(255, 255, 255));
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
