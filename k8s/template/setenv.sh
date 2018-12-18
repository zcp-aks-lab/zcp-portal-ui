out_dir=.tmp

domain_prefix=pou-dev-
domain_iam=${domain_prefix}iam.cloudzcp.io
domain_console=console.cloudzcp.io

namespace=console
image=cloudzcp/zcp-portal-ui:1.1.0-alpha
replicas=0

config_product=       #eg. 'ZCP :)'
config_label=pou-dev  #eg. 'pou-dev'

private_alb=$(kubectl get deploy -n kube-system | grep private | cut -f1 -d' ' | head -n 1)
private_alb_enable='#'  # $([ -z "$private_alb" ] && echo '#' || echo '' )
