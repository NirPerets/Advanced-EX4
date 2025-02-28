import { useState } from "react"

export default function Faq() {
    const questions = [
        {
            question: 'What is Netflix?',
            answer: "Netflix is a streaming service that offers a wide variety of award-winning TV shows, movies, anime, documentaries, and more on thousands of internet-connected devices. You can watch as much as you want, whenever you want without a single commercial – all for one low monthly price. There's always something new to discover and new TV shows and movies are added every week!",
        },
        {
            question: 'How much does Netflix cost?',
            answer: "Watch Netflix on your smartphone, tablet, Smart TV, laptop, or streaming device, all for one fixed monthly fee. Plans range from ₪32.90 to ₪69.90 a month. No extra costs, no contracts."
        },
        {
            question: 'Where can I watch?',
            answer: "Watch anywhere, anytime. Sign in with your Netflix account to watch instantly on the web at netflix.com from your personal computer or on any internet-connected device that offers the Netflix app, including smart TVs, smartphones, tablets, streaming media players and game consoles. You can also download your favorite shows with the iOS or Android app. Use downloads to watch while you're on the go and without an internet connection. Take Netflix with you anywhere.",
        },
        {
            question: 'What can i watch on netflix?',
            answer: "Netflix has an extensive library of feature films, documentaries, TV shows, anime, award-winning Netflix originals, and more. Watch as much as you want, anytime you want.",
        },
        {
            question: 'Is Netflix good for kids?',
            answer: "The Netflix Kids experience is included in your membership to give parents control while kids enjoy family-friendly TV shows and movies in their own space. Kids profiles come with PIN-protected parental controls that let you restrict the maturity rating of content kids can watch and block specific titles you don’t want kids to see.",
        }
    ]

    return(
        <div className="faq__wrapper">
            <div className="container">
                <h2 className="section__title">Frequently Asked Questions</h2>
                <div className="faq">

                    {
                        questions.map(question => {
                            return(
                                <FaqBlock question={question} />
                            )
                        })
                    }
                </div>
            </div>
        </div>
    )
}

function FaqBlock({ question }) {
    const [isOpen, setIsOpen] = useState(false)
    const toggle = () => setIsOpen(!isOpen)

    return(
        <div className={"faq__block" + (isOpen ? ' is-open' : '')}>
            <div className="faq__question" onClick={toggle}>
                {question.question}
                <span>+</span>
            </div>
            <div className="faq__answer">{question.answer}</div>
        </div>
    )
}