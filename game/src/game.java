
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;

public class game {

	public static void main(String[] args) {
		Shooting_Frame game = new Shooting_Frame();
	}

}

class Shooting_Frame extends Frame implements Runnable, KeyListener {
	boolean keyUp = false; // 위쪽 화살표가 눌러지지않은채로있다.
	boolean keyDown = false;// 아래쪽 화살표가 눌러지지않은채로있다.
	boolean keyLeft = false;// 왼쪽 화살표가 눌러지지않은채로있다.
	boolean keyRight = false;// 오른쪽 화살표가 눌러지지않은채로있다.
	boolean space = false;
	boolean shift = false;
	boolean p = false;
	boolean et = false;
	boolean r = false;
	int x = 400, y = 300; // 캐릭터의 시작 위치, 그리고 앞으로의 좌표를 받아오기 위한 변수
	int cnt = 0; // 쓰레드의 루프를 세는 변수, 각종 변수를 통제하기 위해 사용된다
	int life = 0; // 목숨
	int mode = 0;
	int selectmode = 1;
	boolean pause = false;
	boolean ghost = false;
	
	Image stdimg = new ImageIcon("학생.png").getImage();
	Image background = new ImageIcon("강의실.png").getImage();
	Image buffimg = null; // 더블버퍼링을 사용하기위한 버퍼이미지를 정의한
	Graphics gc; // 오브젝트들을 그리기 위한 그래픽 툴을 정의한다
	
	int x = 400, y = 300; // 캐릭터의 시작 위치, 그리고 앞으로의 좌표를 받아오기 위한 변수
	
	int mode = 0;
	int selectmode = 1;
	Image level1 = new ImageIcon("초급.jpg").getImage();
	Image level2 = new ImageIcon("중급.png").getImage();
	Image level3 = new ImageIcon("고급.jpg").getImage();
	Image buffimg = null; // 더블버퍼링을 사용하기위한 버퍼이미지를 정의한다
	
	Shooting_Frame() {
		setTitle("A+ or F");
		setSize(800, 600);
		start(); // 쓰레드의 루프를 시작하기 위한 메써드
		setLocation(250, 80);
		setResizable(false); // 사이즈를 조절할 수 없게 만듬
		setVisible(true); // 프레임을 보이게 만듬
		this.addKeyListener(this); // 키리스너를 추가하여 방향키 정보를 받아올 수 있게 한다.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
	}
	public void update(Graphics g) {
		paint(g);
	} // 프레임 내의 버퍼이미지를 이용하여 깜빡임 현상을 완전히 없앱니다.

	public void paint(Graphics g) { // 각종 이미지를 그리기위한메서드를 실행시킨다
		buffimg = createImage(800, 600); // 버퍼이미지를 그린다 (떠블버퍼링을 사용하여 화면의 깜빡임을
											// 없앤다)
		gc = buffimg.getGraphics(); // 버퍼이미지에 대한 그래픽 객채를 얻어온다.
		drawimages(g);
	}
	
	public void start() {
		Thread th = new Thread(this); // 쓰레드 를 정의
		th.start(); // 쓰레드의 루프를 시작시킨다
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			et = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyRight = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			et = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (life == 0) { // life가 0이하로 떨어지면 루프가 끝난다
			try {
				arrowkey(); // 받은 키에 따른 캐릭터의 이동을 통제
				repaint(); // 리페인트함수(그림을 그때그때 새로기리기위함)
				Thread.sleep(20); // 20밀리세컨드당 한번의 루프가 돌아간다
				if (pause == false) {
					if (mode != 0) {
						if (r == true) {
							//reset();// r을 누를시 리셋됩니다.
						}
						cnt++; // 루프가 돌아간 횟수
					}
				}
			} catch (Exception e) {
			}
		}
	}
	
	public void drawimages(Graphics g) {
			backgroundDrawImg(); // 배경의 그림을 그린다
			g.drawImage(buffimg, 0, 0, this); // 버퍼이미지를 그린다. 0,0으로 좌표를 맞춰서프레임크기에
												// 딱맞춘다
	}
	
	public void backgroundDrawImg() {
		gc.drawImage(background, 0, 0, this); // 가져온 배경이미지파일을 0,0에 위치시킨다
	}
	public void stdDrawImg() {
		MovestdImage(stdimg, x, y, 20, 30); // 캐릭터의 이미지를 좌표에 따라 그린다(루프가돌아가면서
											// 계속다시그리므로 움직이는것처럼 보임),크기는 20x30
	}
	public void MovestdImage(Image stdimg, int x, int y, int width, int height) {
		gc.setClip(x, y, width, height); // 캐릭터의이미지의 좌표와 크기를 받아온다
		gc.drawImage(stdimg, x, y, this); // 캐릭터를 좌표에 따라 장소를 바꾸어 그린다.
	}
	
	public void arrowkey() { // 캐릭터의 이동속도와 방향키에 따른 이동방향을 결정하고, 캐릭터를 화면
		if (mode == 0) {  // 밖으로못빠져나가가게합니다. 그리고 부스터 또한 통제합니다
			if (keyLeft == true) {
				selectmode--;
				if (selectmode == 0) {
					selectmode = 3;
				}
				keyLeft = false;
			}
			if (keyRight == true) {
				selectmode++;
				if (selectmode == 4) {
					selectmode = 1;
				}
				keyRight = false;
			}
		} else {
			if (keyUp == true && pause == false) {
				if (y > 0) {
					if (space == false) {
						y -= 8;
					} else {
						y -= 15;
					}
				}
			}
			if (keyDown == true && pause == false) {
				if (y < 570) {
					if (space == false) {
						y += 8;
					} else {
						y += 15;
					}
				}
			}
			if (keyLeft == true && pause == false) {
				if (x > 0) {
					if (space == false) {
						x -= 8;
					} else {
						x -= 15;
					}
				}
			}
			if (keyRight == true && pause == false) {
				if (x < 780) {
					if (space == false) {
						x += 8;
					} else {
						x += 15;
					}
				}
			}
			
			if (et == true) {
				mode = selectmode;
			}
		}
	}
	
	
}

