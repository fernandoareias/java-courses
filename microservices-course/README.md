docker run -d -v /var/run/docker.sock:/var/run/docker.sock:ro \
-v /proc/:/host/proc/:ro \
-v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro \
-p 127.0.0.1:8126:8126/tcp \
-e DD_API_KEY=<KEY> \
-e DD_SITE="us5.datadoghq.com" \
datadog/agent:latest

