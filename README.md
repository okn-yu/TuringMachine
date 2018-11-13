# 計算複雑性理論の実装
## オートマトンの実装
### 10月27日

- 数学的な理論としては正常系しかないが、工学的観点では実装時に準正常系の動作も考慮する必要がある
- チューリングマシンにせよオートマトンにせよ「状態」と「遷移」が本質的。こういう本質がソースコードに落とすと見えてくるのが実装の面白いところ
- アルファベットや状態遷移関数を数学的に厳密な実装が必ずしもソースコードの品質を高めることにはつながらない。無駄に煩雑になる場合もある
- 例えば各状態が受け取る文字列は実際にはJavaではChar型であるが、Char型を新しくAlphabet型として定義しなおすことには実質的には意味がない
- 状態遷移関数のとしてAlphabetを定義してもそれを参照する必要性はうすく、用途は型チェックくらいにしか使えない
- Stateクラスに状態変異メソッドをもたせようかと思ったけど、そうするとDFA/NFA/Turingマシン毎に実装に差分が生じる
- Stateを抽象クラス/インタフェースにするかクラスを完全に分離するのかのどちらか
- StateSetを単にエイリアス目的でHashSetの継承にした。Java的にはあまりお行儀がよくないらしい...

### 10月28日
- ようやく基本的な実装方針がきまった
- DFAとNFAのたった2種類なのに継承を用いて丁寧に実装しようとすると意外に難しい
- 簡単な設計でさえもデザインパターンの基本的な知識がないとできないのな
- NFAで並列遷移を実装する場合、状態に文字列を埋め込む場合ははコピーを生成しないと同一状態だと衝突する
- 文字列は状態のメンバとしてもたせることにする
- つまるところ状態遷移図を実装できればよい
- そのため最初に各状態のインスタンスを生成する
- 各状態には自分の遷移関数を保持する
- 各状態には現在の文字列を保持する
- 遷移関数はDFAでは状態を返すがNFAでは現在の状態のコピーを返す
- 状態は遷移関数と現在の文字列を参照して別状態に遷移する
- デザインパターンはBridgeパターンを採用する
- オブジェクト指向のメリットの1つは呼び出し側がサブクラス内部の実装を意識せずにすむこと

## 10月29日
- 以前よりもDFAの可読性が大幅に向上した。本質を見極めてコーディングすると全然違う！

## 11月1日
- 検査例外を追加した。純粋な数学のモデルとしては例外はありえないのだけどJavaの勉強のため。

## 11月2日
- Stateにclone実装したけどめっちゃ大変。Javaで実装すると実装が「重くなく」のは避けられないのだと納得。
- multiThreadもmultiProcessある意味ではcloneに近い。cloneを使って擬似的に並列処理を実施した。
- 予期せぬところからくるぬるぽに苦戦。きちんと例外処理しないと。。
- ようやくNFAの実装が形になった。

## 11月3日
- ソースコードの微修正。細かい修正がまだあちこちに残ってる。
- スタックとキューを状態クラスにstaticnに実装すればNFAとDFAの処理を同じ形式にできる。staticはグローバル変数っぽい。

## 11月4日
- UMLでクラス図を作成中。初めてクラス図のありがたみがわかった。オブジェクト指向で実装する場合はクラス図が設計図なのね。。
- 「クラス間の役割分担」と「実装方法」は切っても切り離せない関係にある。両者を一緒にやると一方の修正がもう片方に影響するため終わらない。
- その点クラス図がしっかりしていれば実装では実装以外で頭を悩ます必要はない。
- つまりオブジェクト指向でそれなりのものを作ろうとした場合はクラス図作成は必須。
- Automatonが神クラスにならないように状態クラスを作成したが機能が重複しているのがよく分かる。
- まるでクラス図は衛星写真のようだ。なにがまずいかがはっきり可視化される。
- 今気付いたけどδはハッシュマップでなくクラスを作成したほうが見通しがよかった。今度はそれで実装しよう。
- 今までの悩みが氷解した。オートマトンと状態クラスの役割が重複していたのが最大の問題。そのため状態に機能を追加するとオートマトンの役割がほぼなくなり逆も同じ。両方実装すると役割が重複する。
- クラス図でようやく解が見えた。オートマトンの形式にそうならば状態クラスは大幅削減が答え。クラス図書かないと決まらなかった気がする。クラス図本当に便利。

## 11月5日
- UMLを修正中。UMLで設計できるってすごいなぁ。

## 11月6日
- Javaの外部ライブラリを使えばPairクラスやTupletも利用できるのか。dletaの実装に利用できる。自分でクラスを作るのもあり。

## 11月8日
- 結局既存のコードは全部捨てて書き直し。練習用のコーディングだししかたないね。。

## 11月9日
- NFAは状態クラスを捨てた結果としてその分メソッド処理が重くなった。どこかで実装する必要があるのでしかたない。

## 11月10日
- PDAの状態遷移処理の分岐が重い。
- クラス図の見通しが良くても実装が重くなる可能性がある。クラス図だけからでは処理の内部分岐までは見えないから。
- Javaで継承を頑張るとあちこちにジェネリクスが頻出するコードに。そうなる運命だったのか。。。
- とにかくいろいろ勉強いなるな。

## 11月11日
- PDAの実装が思ったよりも重い。これを綺麗に書ききれるかが重大なポイントとなる。
- PDAがまるで手続き型言語のような処理になっている。
- PDAだけ文字列とスタックを持ちスタックの動作が複雑なので、これだけクラスとして外に切り出すことにした。
- これでソースコードの可読性があがるはず。

## 11月12日
- PDAStateクラスに状態遷移をどう実装するかで悩み中。。

## 11月13日
- PDAの実装がようやく形になってきた。もう少し綺麗にまとめることもできそうなのが気になるなぁ。