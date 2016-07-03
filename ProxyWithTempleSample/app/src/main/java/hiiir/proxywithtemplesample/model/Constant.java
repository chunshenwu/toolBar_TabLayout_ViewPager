
package hiiir.proxywithtemplesample.model;

public class Constant {

    public static class CardContract {

        public enum CardType {
            SMALL_CARD,
            MEDIUM_CARD,
            BIG_CARD
        }

        public enum CardId {
            NONE(-1),
            Card1(1001),
            Card2(1002),
            Card3(1003),
            Card4(1004),
            Card5(1005),
            ;

            private int mId;
            CardId(int id) {
                mId = id;
            }

            public int getId() {
                return mId;
            }
        }
    }
}
