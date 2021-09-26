## Inspiration

One of the developers wanted to create a project that would solve a real pain
the point that he experienced every day, at work, online, and in a private circle:

We lost our ability to differentiate between reality and alternative facts,
to acknowledge our limitations, and to be convinced to change opinion.

This leads us to follow unhealthy behaviors, to be more fragile
(physically and mentally) and to be less efficient.

To contextualize the challenge, it should be underlined that the four years
preceding it, a pathological liar was the president of the USA, many of his
supporters are denying the reality that he lost the elections, the same people
are considering a climate crisis caused by humans is happening as a hoax and
that COVID-19 is just the flu, that can be healed with vitamins, homeopathy, or
bleach and that its mRNA vaccines are causing all types of undesirable side
effects.

How does one know that his representation of reality is faithful to it? Which
techniques should be used to verify the veracity of one's knowledge? Is anyone,
even the most intelligent, protected from believing in falsehoods?

Our work tries to present the scientific methods as a possible solution to
reducing the risk of believing in false things, and increasing our
chance to make better decisions about our health and our relationships, and also
be much more tolerant towards ourselves which leads to better resiliency.

## What it does

The first part shows that users might be victims of cognitive biases and
fallacious argumentation, and try to show how he can protect himself from
these. We also explain how we could leverage our biases to nudge ourselves
to breath deeply more often, exercise, and improve our diet.

The second part is a sketch on how we could gamify the whole process, by rating
our interaction (online, physical, or during a meeting).

## How we built it

We created a front-end web application as we tried to push as much logic and
computation on the client. This has the benefit of being extremely scalable and
keeping the user's privacy.

We use ClojureScript (a powerful lisp language that targets JavaScript),
re-frame and datascript (a datalog in-memory database) to manage the state of
the application, reagent, and react for displaying components. As for the
visualization, we leveraged plotly.js. The Google Closure Compiler is then
used to reduce the size of the artifacts and to split the application for
faster loading. We also used `webp` to compress images size.

As for the deployment, we use the free tier of netlify as using git/github as
our DevOps platform had many benefits given the size of the team and project.

## Challenges we ran into

Making a review of the topic of cognitive bias, fallacies, and failures of the
scientific methods in less than 24 hours were challenging.

The topic is also quite difficult to pitch as it is quite abstract.

One of the developers also had for the first time the responsibility to take
care of his baby which limited our ability to develop.

## Accomplishments that we're proud of

It is a functional and responsive website and its structure is near final. We
basically missed the time to fill the app with content. With a bit of time, it could
become a fine introduction to the scientific methods and make people think
about their beliefs.

## What we learned

We had to review our content and material multiple times.

- Cognitive bias: everyone can be a victim of their own brain, and it requires
  hard work to reduce its effects. The most frightening observation is that our
  brain compresses our memory and when reconstructing memory usually fills the
  the gap with the most likely expectations, which might be totally opposite to
  reality.
- Rhetorical and logical fallacies are commonly accepted as valid arguments by
  most leading to sterile discussions.
- Survival bias and publication bias for medicine are underestimated and
  that could be a public hazard issue.

On the technical side, given the timeframe, we, unfortunately, did not take any
risks, but then, our tech stack is fairly unconventional anyways.

## What's next for Neo the Doubtful: The Art of Skepticism

We hope we can fill the website with content so that people might actually use
it and learn something out of it. The scientific method is one of the safest
methods to discover the truth.

On the technical part, it would be extremely interesting to check whether an NLP
model could classify and annotate fallacies from given sentences. Given the
latest advancement in the domain, we would not be surprised if that worked, and
if yes, if we could leverage TensorFlow-js to add it to the website. See
(https://github.com/tensorflow/tfjs-models/tree/master/universal-sentence-encoder)[Universal
Sentence Encoder lite].
