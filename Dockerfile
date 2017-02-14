FROM docker.io/frolvlad/alpine-oraclejdk8

ENV DISCOGS_REFRESH_INTERVAL 336

RUN mkdir -p /usr/local/discogs
WORKDIR /usr/local/discogs

CMD ["bash"]

EXPOSE 8888
EXPOSE 2222
EXPOSE 22
