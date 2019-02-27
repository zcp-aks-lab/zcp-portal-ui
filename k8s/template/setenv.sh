out_dir=.tmp

domain_prefix=pog-dev-
domain_iam=${domain_prefix}iam.cloudzcp.io
domain_console=${domain_prefix}console.cloudzcp.io

namespace=zcp-system
image=registry.au-syd.bluemix.net/cloudzcp/zcp-iam:1.1.0
replicas=1

config_product=ZCP    #eg. 'ZCP :)'
config_label=pou-dev  #eg. 'pou-dev'

private_alb=$(kubectl get deploy -n kube-system | grep private | cut -f1 -d' ' | head -n 1)
private_alb_enable='#'  # $([ -z "$private_alb" ] && echo '#' || echo '' )
